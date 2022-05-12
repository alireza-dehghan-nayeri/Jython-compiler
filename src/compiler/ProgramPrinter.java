package compiler;

import gen.JythonListener;
import gen.JythonParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class ProgramPrinter implements JythonListener {

    Integer indentCount = 0;

    private void varDecField(JythonParser.VarDecContext varDecContext) {
        if (varDecContext != null) {
            String varType = "";
            if (varDecContext.TYPE() != null) {
                varType = varDecContext.TYPE().getText();
            } else if (varDecContext.CLASSNAME() != null) {
                varType = varDecContext.CLASSNAME().getText();
            }
            System.out.println("    ".repeat(indentCount) + "field: " + varDecContext.ID() + "/ type= " + varType);
        }
    }

    @Override
    public void enterProgram(JythonParser.ProgramContext ctx) {
        indentCount++;
        System.out.println("program start{");
    }

    @Override
    public void exitProgram(JythonParser.ProgramContext ctx) {
        indentCount--;
        System.out.println("    ".repeat(indentCount) + "}");
    }

    @Override
    public void enterImportclass(JythonParser.ImportclassContext ctx) {

        System.out.println("    ".repeat(indentCount) + "import class: " + ctx.CLASSNAME());
    }

    @Override
    public void exitImportclass(JythonParser.ImportclassContext ctx) {

    }

    @Override
    public void enterClassDef(JythonParser.ClassDefContext ctx) {

        List<TerminalNode> parents = ctx.CLASSNAME();
        // remove first element as it is the actual class name
        parents.remove(0);
        System.out.print("    ".repeat(indentCount) + "class: " + ctx.CLASSNAME(0) + "/ class parents: ");
        if (!parents.isEmpty()) {
            for (TerminalNode parent : parents) {
                System.out.print(parent + ",");
            }
        }
        System.out.println("{");

        indentCount++;
    }

    @Override
    public void exitClassDef(JythonParser.ClassDefContext ctx) {
        indentCount--;
        System.out.println("    ".repeat(indentCount) + "}");
    }

    @Override
    public void enterClass_body(JythonParser.Class_bodyContext ctx) {

        // field
        varDecField(ctx.varDec());

        // field
        if (ctx.arrayDec() != null) {
            String arrType = "";
            if (ctx.arrayDec().TYPE() != null) {
                arrType = ctx.arrayDec().TYPE().getText();
            } else if (ctx.arrayDec().CLASSNAME() != null) {
                arrType = ctx.arrayDec().CLASSNAME().getText();
            }
            System.out.println("    ".repeat(indentCount) + "field: " + ctx.arrayDec().ID() + "/ type= " + arrType);
        }


    }

    @Override
    public void exitClass_body(JythonParser.Class_bodyContext ctx) {

    }

    @Override
    public void enterVarDec(JythonParser.VarDecContext ctx) {

    }

    @Override
    public void exitVarDec(JythonParser.VarDecContext ctx) {

    }

    @Override
    public void enterArrayDec(JythonParser.ArrayDecContext ctx) {

    }

    @Override
    public void exitArrayDec(JythonParser.ArrayDecContext ctx) {

    }

    @Override
    public void enterMethodDec(JythonParser.MethodDecContext ctx) {

        String methodName = "";
        if (ctx.ID() != null) {
            methodName = ctx.ID().getText();
        }


        // check if the method is main or normal class method:
        if (methodName.equals("main")) {
            System.out.println("    ".repeat(indentCount) + "main method{");

        } else {
            String returnType = "";
            if (ctx.CLASSNAME() != null) {
                returnType = ctx.CLASSNAME().getText();
            }
            if (ctx.TYPE() != null) {
                returnType = ctx.TYPE().getText();
            }


            System.out.print("    ".repeat(indentCount) + "class method: " + ctx.ID());
            if (!returnType.equals("")) {
                System.out.print("/ return type=" + returnType);
            }
            System.out.println("{");


        }
        indentCount++;

    }

    @Override
    public void exitMethodDec(JythonParser.MethodDecContext ctx) {
        indentCount--;
        System.out.println("    ".repeat(indentCount) + "}");
    }

    @Override
    public void enterConstructor(JythonParser.ConstructorContext ctx) {
        String className = "";
        if (ctx.CLASSNAME() != null) {
            className = ctx.CLASSNAME().getText();
        } else if (ctx.TYPE() != null) {
            className = ctx.TYPE().getText();

        }

        System.out.println("    ".repeat(indentCount) + "class constructor: " + className + "{");
        indentCount++;
    }

    @Override
    public void exitConstructor(JythonParser.ConstructorContext ctx) {
        indentCount--;
        System.out.println("    ".repeat(indentCount) + "}");
    }

    @Override
    public void enterParameter(JythonParser.ParameterContext ctx) {
        List<String> parameters = new ArrayList<>();

        for (JythonParser.VarDecContext parameter : ctx.varDec()) {
            String varType = "";
            if (parameter.TYPE() != null) {
                varType = parameter.TYPE().getText();
            } else if (parameter.CLASSNAME() != null) {
                varType = parameter.CLASSNAME().getText();
            }
            parameters.add(varType + " " + parameter.ID());
        }


        System.out.println("    ".repeat(indentCount) + "parameters list: " + parameters);

    }

    @Override
    public void exitParameter(JythonParser.ParameterContext ctx) {

    }

    @Override
    public void enterStatement(JythonParser.StatementContext ctx) {


        // field
        varDecField(ctx.varDec());

        // field : arr declaration is not added in the statements !!


    }

    @Override
    public void exitStatement(JythonParser.StatementContext ctx) {

    }

    @Override
    public void enterReturn_statment(JythonParser.Return_statmentContext ctx) {

    }

    @Override
    public void exitReturn_statment(JythonParser.Return_statmentContext ctx) {

    }

    @Override
    public void enterCondition_list(JythonParser.Condition_listContext ctx) {

    }

    @Override
    public void exitCondition_list(JythonParser.Condition_listContext ctx) {

    }

    @Override
    public void enterCondition(JythonParser.ConditionContext ctx) {

    }

    @Override
    public void exitCondition(JythonParser.ConditionContext ctx) {

    }

    @Override
    public void enterIf_statment(JythonParser.If_statmentContext ctx) {

        System.out.println("    ".repeat(indentCount) + "nested statement{");
        indentCount++;
    }

    @Override
    public void exitIf_statment(JythonParser.If_statmentContext ctx) {

        indentCount--;
        System.out.println("    ".repeat(indentCount) + "}");
    }

    @Override
    public void enterWhile_statment(JythonParser.While_statmentContext ctx) {

        System.out.println("    ".repeat(indentCount) + "nested statement{");
        indentCount++;
    }

    @Override
    public void exitWhile_statment(JythonParser.While_statmentContext ctx) {
        indentCount--;
        System.out.println("    ".repeat(indentCount) + "}");
    }

    @Override
    public void enterIf_else_statment(JythonParser.If_else_statmentContext ctx) {

        System.out.println("    ".repeat(indentCount) + "nested statement{");
        indentCount++;
    }

    @Override
    public void exitIf_else_statment(JythonParser.If_else_statmentContext ctx) {
        indentCount--;
        System.out.println("    ".repeat(indentCount) + "}");
    }

    @Override
    public void enterPrint_statment(JythonParser.Print_statmentContext ctx) {

    }

    @Override
    public void exitPrint_statment(JythonParser.Print_statmentContext ctx) {

    }

    @Override
    public void enterFor_statment(JythonParser.For_statmentContext ctx) {
        System.out.println("    ".repeat(indentCount) + "nested statement{");
        indentCount++;
    }

    @Override
    public void exitFor_statment(JythonParser.For_statmentContext ctx) {
        indentCount--;
        System.out.println("    ".repeat(indentCount) + "}");
    }

    @Override
    public void enterMethod_call(JythonParser.Method_callContext ctx) {

    }

    @Override
    public void exitMethod_call(JythonParser.Method_callContext ctx) {

    }

    @Override
    public void enterAssignment(JythonParser.AssignmentContext ctx) {

    }

    @Override
    public void exitAssignment(JythonParser.AssignmentContext ctx) {

    }

    @Override
    public void enterExp(JythonParser.ExpContext ctx) {

    }

    @Override
    public void exitExp(JythonParser.ExpContext ctx) {

    }

    @Override
    public void enterPrefixexp(JythonParser.PrefixexpContext ctx) {

    }

    @Override
    public void exitPrefixexp(JythonParser.PrefixexpContext ctx) {

    }

    @Override
    public void enterArgs(JythonParser.ArgsContext ctx) {

    }

    @Override
    public void exitArgs(JythonParser.ArgsContext ctx) {

    }

    @Override
    public void enterExplist(JythonParser.ExplistContext ctx) {

    }

    @Override
    public void exitExplist(JythonParser.ExplistContext ctx) {

    }

    @Override
    public void enterArithmetic_operator(JythonParser.Arithmetic_operatorContext ctx) {

    }

    @Override
    public void exitArithmetic_operator(JythonParser.Arithmetic_operatorContext ctx) {

    }

    @Override
    public void enterRelational_operators(JythonParser.Relational_operatorsContext ctx) {

    }

    @Override
    public void exitRelational_operators(JythonParser.Relational_operatorsContext ctx) {

    }

    @Override
    public void enterAssignment_operators(JythonParser.Assignment_operatorsContext ctx) {

    }

    @Override
    public void exitAssignment_operators(JythonParser.Assignment_operatorsContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
