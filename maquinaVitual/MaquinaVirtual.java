package maquinaVitual;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MaquinaVirtual {
    private LineNumberReader lr;
    private List<Instrucao> program;
    private HashMap<String, Integer> labelMemory;
    private Integer[] memory;

    public MaquinaVirtual(LineNumberReader lr) {
        this.lr = lr;
    }

    public void loadPC() throws IOException {
        var line = lr.readLine();
        program = new ArrayList<>();
        labelMemory = new HashMap<>();
        while (line != null) {
            Instrucao instrucao = new Instrucao(line);
            program.add(instrucao);

            if (instrucao.getComando().equals("NULL")) {
                labelMemory.put(instrucao.getLabel(), program.indexOf(instrucao));
            }

            line = lr.readLine();
        }
    }

    public void execute() {
        memory = new Integer[1000];
        int memory_pointer = 0;
        int pc = 0;
        for (; pc < program.size(); pc++) {
            Instrucao instrucao = program.get(pc);
            if (instrucao.getComando().equals("START")) {
                memory_pointer += 1;
            } else if (instrucao.getComando().equals("LDC")) {
                Integer op1 = Integer.parseInt(instrucao.getParameter1());

                memory_pointer += 1;
                memory[memory_pointer] = op1;
            } else if (instrucao.getComando().equals("LDV")) {
                Integer op1 = Integer.parseInt(instrucao.getParameter1());

                memory_pointer += 1;
                memory[memory_pointer] = memory[op1];
            } else if (instrucao.getComando().equals("ADD")) {
                Integer op1 = memory[memory_pointer - 1];
                Integer op2 = memory[memory_pointer];

                memory[memory_pointer - 1] = op1 + op2;
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("SUB")) {
                Integer op1 = memory[memory_pointer - 1];
                Integer op2 = memory[memory_pointer];

                memory[memory_pointer - 1] = op1 - op2;
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("MULT")) {
                Integer op1 = memory[memory_pointer - 1];
                Integer op2 = memory[memory_pointer];

                memory[memory_pointer - 1] = op1 * op2;
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("DIVI")) {
                Integer op1 = memory[memory_pointer - 1];
                Integer op2 = memory[memory_pointer];

                memory[memory_pointer - 1] = op1 / op2;
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("INV")) {
                memory[memory_pointer] = -memory[memory_pointer];
            } else if (instrucao.getComando().equals("AND")) {
                if (memory[memory_pointer - 1] == 1 && memory[memory_pointer] == 1) {
                    memory[memory_pointer - 1] = 1;
                } else {
                    memory[memory_pointer - 1] = 0;
                }
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("OR")) {
                if (memory[memory_pointer - 1] == 1 || memory[memory_pointer] == 1) {
                    memory[memory_pointer - 1] = 1;
                } else {
                    memory[memory_pointer - 1] = 0;
                }
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("NEG")) {
                memory[memory_pointer] = 1 - memory[memory_pointer];
            } else if (instrucao.getComando().equals("CME")) {
                int op1 = memory[memory_pointer - 1];
                int op2 = memory[memory_pointer];

                if (op1 < op2) {
                    memory[memory_pointer - 1] = 1;
                } else {
                    memory[memory_pointer - 1] = 0;
                }
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("CMA")) {
                int op1 = memory[memory_pointer - 1];
                int op2 = memory[memory_pointer];

                if (op1 > op2) {
                    memory[memory_pointer - 1] = 1;
                } else {
                    memory[memory_pointer - 1] = 0;
                }
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("CEQ")) {
                int op1 = memory[memory_pointer - 1];
                int op2 = memory[memory_pointer];

                if (op1 == op2) {
                    memory[memory_pointer - 1] = 1;
                } else {
                    memory[memory_pointer - 1] = 0;
                }
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("CDIF")) {
                int op1 = memory[memory_pointer - 1];
                int op2 = memory[memory_pointer];

                if (op1 != op2) {
                    memory[memory_pointer - 1] = 1;
                } else {
                    memory[memory_pointer - 1] = 0;
                }
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("CMEQ")) {
                int op1 = memory[memory_pointer - 1];
                int op2 = memory[memory_pointer];

                if (op1 <= op2) {
                    memory[memory_pointer - 1] = 1;
                } else {
                    memory[memory_pointer - 1] = 0;
                }
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("CMAQ")) {
                int op1 = memory[memory_pointer - 1];
                int op2 = memory[memory_pointer];

                if (op1 >= op2) {
                    memory[memory_pointer - 1] = 1;
                } else {
                    memory[memory_pointer - 1] = 0;
                }
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("STR")) {
                int op = Integer.parseInt(instrucao.getParameter1());
                memory[op] = memory[memory_pointer];
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("JMP")) {
                pc = labelMemory.get(instrucao.getParameter1());
            } else if (instrucao.getComando().equals("JMPF")) {
                if (memory[memory_pointer] == 0) {
                    pc = labelMemory.get(instrucao.getParameter1());
                }
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("HLT")) {
                return;
            } else if (instrucao.getComando().equals("ALLOC")) {
                int op1 = Integer.parseInt(instrucao.getParameter1());
                int op2 = Integer.parseInt(instrucao.getParameter2());
                for (int k = 0; k < op2; k++) {
                    memory_pointer += 1;
                    memory[memory_pointer] = memory[op1 + k];
                }
            } else if (instrucao.getComando().equals("DALLOC")) {
                int op1 = Integer.parseInt(instrucao.getParameter1());
                int op2 = Integer.parseInt(instrucao.getParameter2());
                for (int k = 0; k < op2; k++) {
                    memory[op1 + k] = memory[memory_pointer];
                    memory_pointer -= 1;
                }
            } else if (instrucao.getComando().equals("CALL")) {
                memory_pointer += 1;
                memory[memory_pointer] = pc + 1;
                pc = labelMemory.get(instrucao.getParameter1());
            } else if (instrucao.getComando().equals("RETURN")) {
                pc = memory[memory_pointer] - 1;
                memory_pointer -= 1;
            } else if (instrucao.getComando().equals("RETURNF")) {
                pc = memory[memory_pointer];

                memory[memory_pointer] = memory[0];
            } else if (instrucao.getComando().equals("RD")) {
                Scanner input = new Scanner(System.in);
                memory[memory_pointer] = Integer.parseInt(input.nextLine());
            } else if (instrucao.getComando().equals("PRN")) {
                System.out.println(memory[memory_pointer]);
            }
        }
    }

    public void setLr(LineNumberReader lr) {
        this.lr = lr;
    }
}
