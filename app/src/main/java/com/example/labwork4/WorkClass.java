package com.example.labwork4;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class WorkClass {

    public List<Double> solve(String expression, double a, double b, double eps) {

        int n = 100;
        int k = 0;
        List<Double> res = new ArrayList<>();

        String fA;
        String fB;

        if (a < 0) {
            fA = expression.replace("x", "(-) " + (Math.abs(a)));
        } else fA = expression.replace("x", Double.toString(a));
        if (b < 0) {
            fB = expression.replace("x", "(-) " + b);
        } else fB = expression.replace("x", Double.toString(b));

        try {
            if (evaluate(fA) * evaluate(fB) >= 0) {
                double temp = a;
                a = b;
                b = temp;
            }
            while (k < n) {
                double c = (a + b) / 2;
                String fc;
                if (c < 0) {
                    fc = expression.replace("x", "(-) " + (Math.abs(c)));
                } else {
                    fc = expression.replace("x", Double.toString(c));
                }
                if (evaluate(fc) == 0) {
                    res.add(roundNum(c));
                }
                if (Math.abs((b - a)) < eps) {
                    res.add(roundNum(c));
                }
                if (evaluate(fA) * evaluate(fc) < 0) {
                    b = c;
                } else {
                    a = c;
                }
                k++;
            }

            return res;
        } catch (Exception e) {
            System.out.println("Помилка!");
        }
        return null;

    }

    private static double evaluate(String expression) {
        String newExpression = expression.replace("(-)", "_ ");
        newExpression = newExpression + ' ';
        char[] entr = newExpression.toCharArray();
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < entr.length; i++) {
            if (entr[i] == ' ')
                continue;


            if (entr[i] >= '0' && entr[i] <= '9' || entr[i] == '.' || entr[i] == '_') {
                StringBuilder stringBuilder = new StringBuilder();

                if (entr[i] == '_') {
                    stringBuilder.append(0.0);
                    operators.push('-');
                }
                while (i < entr.length && entr[i] >= '0' && entr[i] <= '9' || entr[i] == '.')
                    stringBuilder.append(entr[i++]);
                values.push(Double.parseDouble(stringBuilder.toString()));


            } else if (entr[i] == '(') {
                operators.push(entr[i]);

            } else if (entr[i] == ')') {
                while (operators.peek() != '(')
                    values.push((applyOp(operators.pop(), values.pop(), values.pop())));
                operators.pop();
            } else if (entr[i] == '+' || entr[i] == '-' || entr[i] == '*' || entr[i] == '/' || entr[i] == '^') {
                while (!operators.empty() && hasPrecedence(entr[i], operators.peek())) {

                    values.push((applyOp(operators.pop(), values.pop(), values.pop())));
                }
                operators.push(entr[i]);
            }
        }

        while (!operators.empty()) {
            values.push(applyOp(operators.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }

    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op1 != '^' && op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    private static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '^':
                return Math.pow(a, b);
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Can not divide by zero!");
                }
                return a / b;

        }
        return 0.0;
    }

    private double roundNum(double value) {
        double scale = Math.pow(10, 2);
        return Math.round(value * scale) / scale;
    }


    public static void main(String[] args) {
        try {
            WorkClass a = new WorkClass();
            double result = a.solve("2 ^ ( x ) - 4 * x", 0, 4, 0.01).get(0); // spaces are required!!!
            System.out.println(result);
        } catch (Exception e) {
            System.out.println();
        }
    }

}
