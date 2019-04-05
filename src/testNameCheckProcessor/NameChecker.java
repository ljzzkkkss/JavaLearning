package testNameCheckProcessor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner6;

import java.util.EnumSet;

import static javax.lang.model.element.ElementKind.*;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static javax.tools.Diagnostic.Kind.WARNING;

/**
 * Created by ljzzkkkss on 2018/4/23.
 */
public class NameChecker {
    private final Messager messager;

    NameCheckScanner nameCheckScanner = new NameCheckScanner();

    NameChecker(ProcessingEnvironment processingEnv) {
        this.messager = processingEnv.getMessager();
    }

    public void checkNames(Element element){
        nameCheckScanner.scan(element);
    }

    private class NameCheckScanner extends ElementScanner6<Void, Void>{
        @Override
        public Void visitType(TypeElement e, Void p) {
            scan(e.getTypeParameters(),p);
            checkCamelCase(e, true);
            super.visitType(e, p);
            return null;
        }

        @Override
        public Void visitExecutable(ExecutableElement e, Void p){
            if(e.getKind() == METHOD) {
                Name name = e.getSimpleName();
                if(name.contentEquals(e.getEnclosingElement().getSimpleName())){
                    messager.printMessage(WARNING,"一个普通方法\"" + name + "\"不应该与当前类名重复，避免与构造函数产生混淆", e);
                    checkCamelCase(e, false);
                }
            }
            super.visitExecutable(e,p);
            return null;
        }

        @Override
        public Void visitVariable(VariableElement e, Void p){
            if (e.getKind() == ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e)){
                checkAllCaps(e);
            }else{
                checkCamelCase(e, false);
            }
            return null;
        }

        private boolean heuristicallyConstant(VariableElement e){
            if(e.getEnclosingElement().getKind() == INTERFACE){
                return true;
            }else if(e.getKind() == FIELD && e.getModifiers().containsAll(EnumSet.of(PUBLIC,STATIC,FINAL))){
                return true;
            }else {
                return false;
            }
        }

        private void checkCamelCase(Element e,boolean initialCape){
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if(Character.isUpperCase(firstCodePoint)){
                previousUpper = true;
                if(!initialCape){
                    messager.printMessage(WARNING,"名称\"" + name + "\"应当以小写字母开头", e);
                    return;
                }
            }else if(Character.isLowerCase(firstCodePoint)){
                previousUpper = false;
                if(initialCape){
                    messager.printMessage(WARNING,"名称\"" + name + "\"应当以大写字母开头", e);
                    return;
                }
            }else{
                conventional = false;
            }

            if(conventional){
                int cp = firstCodePoint;
                for(int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)){
                    cp = name.codePointAt(i);
                    if(Character.isUpperCase(cp)){
                        if(previousUpper){
                            conventional = false;
                            break;
                        }
                        previousUpper = true;
                    }else{
                        previousUpper = false;
                    }
                }
            }

            if(!conventional){
                messager.printMessage(WARNING,"名称\"" + name + "\"应当符合驼峰命名法", e);
            }
        }
    }

    private void checkAllCaps(Element e){
        String name = e.getSimpleName().toString();
        boolean previousUpper = false;
        boolean conventional = true;
        int firstCodePoint = name.codePointAt(0);

        if(!Character.isUpperCase(firstCodePoint)) {
            conventional = false;
        }else{
            boolean previousUnderscore = false;
            int cp = firstCodePoint;
            for(int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)){
                cp = name.codePointAt(i);
                if(cp == (int) '_'){
                    if(previousUnderscore){
                        conventional = false;
                        break;
                    }
                    previousUnderscore = true;
                } else {
                    previousUnderscore = false;
                    if(!Character.isUpperCase(cp) && !Character.isDigit(cp)){
                        conventional = false;
                        break;
                    }
                }
            }
        }

        if(!conventional){
            messager.printMessage(WARNING,"常量\"" + name + "\"应当全部以大写字母或下划线命名，并以大写字母开头", e);
        }
    }
}
