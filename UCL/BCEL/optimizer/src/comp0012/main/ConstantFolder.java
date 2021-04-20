package comp0012.main;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class ConstantFolder {
    ClassParser parser = null;
    ClassGen gen = null;
    private boolean isOptimised;

    JavaClass original = null;
    JavaClass optimized = null;

    public ConstantFolder(String classFilePath) {
        try {
            this.parser = new ClassParser(classFilePath);
            this.original = this.parser.parse();
            this.gen = new ClassGen(this.original);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void safeDelete(InstructionHandle ih, InstructionList instList) {
        try {
            instList.delete(ih);
        } catch (TargetLostException e) {
            e.printStackTrace();
        }
    }

    private void safeDelete(InstructionHandle from, InstructionHandle to, InstructionList instList) {
        try {
            instList.delete(from, to);
        } catch (TargetLostException e) {
            e.printStackTrace();
        }
    }

    private boolean ifUnaryOptimise(InstructionHandle instructionHandle, int value, int index, InstructionHandle[] handles, InstructionList instList) {
        IfInstruction instruction = (IfInstruction) instructionHandle.getInstruction();
        boolean flag = false;
        int length = handles.length;
        if (instruction instanceof IFEQ && value == 0 || instruction instanceof IFGE && value >= 0
                || instruction instanceof IFLE && value <= 0 || instruction instanceof IFGT && value > 0
                || instruction instanceof IFLT && value < 0 || instruction instanceof IFNE && value != 0) {
            flag = true;
        }
        if (findTargetIndex(length, handles, instruction.getTarget()) < index) {
            return false;
        }
        InstructionHandle gotoTarget = null;
        InstructionHandle ifTarget = instruction.getTarget();
        if (ifTarget.getPrev().getInstruction() instanceof GotoInstruction) {
            gotoTarget = ((GotoInstruction) ifTarget.getPrev().getInstruction()).getTarget();
            if (findTargetIndex(length, handles, gotoTarget) < findTargetIndex(length, handles, ifTarget) - 1) {
                return false;
            }
        }
        if (flag) {
            safeDelete(handles[index - 1], ifTarget.getPrev(), instList);
        } else {
            safeDelete(handles[index - 1], handles[index], instList);
            if (gotoTarget != null) {
                safeDelete(ifTarget.getPrev(), gotoTarget.getPrev(), instList);
            }
        }
        return true;
    }

    private boolean ifBinaryOptimise(IfInstruction instruction, int x, int y, int index, InstructionHandle[] handles, InstructionList instList) {
        boolean flag = false;
        int length = handles.length;
        if (instruction instanceof IF_ICMPEQ && x == y || instruction instanceof IF_ICMPGE && x >= y
                || instruction instanceof IF_ICMPLE && x <= y || instruction instanceof IF_ICMPGT && x > y
                || instruction instanceof IF_ICMPLT && x < y || instruction instanceof IF_ICMPNE && x != y) {
            flag = true;
        }
        if (findTargetIndex(length, handles, instruction.getTarget()) < index) {
            return false;
        }
        InstructionHandle gotoTarget = null;
        InstructionHandle ifTarget = instruction.getTarget();
        if (ifTarget.getPrev().getInstruction() instanceof GotoInstruction) {
            gotoTarget = ((GotoInstruction) ifTarget.getPrev().getInstruction()).getTarget();
            if (findTargetIndex(length, handles, gotoTarget) < findTargetIndex(length, handles, ifTarget) - 1) {
                return false;
            }
        }
        if (flag) {
            safeDelete(handles[index - 2], ifTarget.getPrev(), instList);
        } else {
            safeDelete(handles[index - 2], handles[index], instList);
            if (gotoTarget != null) {
                safeDelete(ifTarget.getPrev(), gotoTarget.getPrev(), instList);
            }
        }
        return true;
    }

    private int findTargetIndex(int length, InstructionHandle[] handles, InstructionHandle handle) {
        for (int x = 0; x < length; x++) {
            if (handles[x].equals(handle))
                return x;
        }
        return -1;
    }

    private int calculate(PushInstruction handleX, PushInstruction handleY, Instruction operand, ConstantPoolGen cpgen) {
        if (operand instanceof IADD) {
            int x;
            int y;
            if (handleX instanceof CPInstruction) {
                x = (int) ((LDC) handleX).getValue(cpgen);
            } else {
                x = (int) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (int) ((LDC) handleY).getValue(cpgen);
            } else {
                y = (int) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantInteger(x + y), cpgen);
        } else if (operand instanceof ISUB) {
            int x;
            int y;
            if (handleX instanceof CPInstruction) {
                x = (int) ((LDC) handleX).getValue(cpgen);
            } else {
                x = (int) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (int) ((LDC) handleY).getValue(cpgen);
            } else {
                y = (int) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantInteger(x - y), cpgen);
        } else if (operand instanceof IMUL) {
            int x;
            int y;
            if (handleX instanceof CPInstruction) {
                x = (int) ((LDC) handleX).getValue(cpgen);
            } else {
                x = (int) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (int) ((LDC) handleY).getValue(cpgen);
            } else {
                y = (int) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantInteger(x * y), cpgen);
        } else if (operand instanceof IDIV) {
            int x;
            int y;
            if (handleX instanceof CPInstruction) {
                x = (int) ((LDC) handleX).getValue(cpgen);
            } else {
                x = (int) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (int) ((LDC) handleY).getValue(cpgen);
            } else {
                y = (int) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantInteger(x / y), cpgen);
        } else if (operand instanceof FADD) {
            float x;
            float y;
            if (handleX instanceof CPInstruction) {
                x = (float) ((LDC) handleX).getValue(cpgen);
            } else {
                x = (float) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (float) ((LDC) handleY).getValue(cpgen);
            } else {
                y = (float) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantFloat(x + y), cpgen);
        } else if (operand instanceof FSUB) {
            float x;
            float y;
            if (handleX instanceof CPInstruction) {
                x = (float) ((LDC) handleX).getValue(cpgen);
            } else {
                x = (float) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (float) ((LDC) handleY).getValue(cpgen);
            } else {
                y = (float) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantFloat(x - y), cpgen);
        } else if (operand instanceof FMUL) {
            float x;
            float y;
            if (handleX instanceof CPInstruction) {
                x = (float) ((LDC) handleX).getValue(cpgen);
            } else {
                x = (float) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (float) ((LDC) handleY).getValue(cpgen);
            } else {
                y = (float) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantFloat(x * y), cpgen);
        } else if (operand instanceof FDIV) {
            float x;
            float y;
            if (handleX instanceof CPInstruction) {
                x = (float) ((LDC) handleX).getValue(cpgen);
            } else {
                x = (float) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (float) ((LDC) handleY).getValue(cpgen);
            } else {
                y = (float) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantFloat(x / y), cpgen);
        } else if (operand instanceof LADD) {
            long x;
            long y;
            if (handleX instanceof CPInstruction) {
                x = (long) ((LDC2_W) handleX).getValue(cpgen);
            } else {
                x = (long) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (long) ((LDC2_W) handleY).getValue(cpgen);
            } else {
                y = (long) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantLong(x + y), cpgen);
        } else if (operand instanceof LSUB) {
            long x;
            long y;
            if (handleX instanceof CPInstruction) {
                x = (long) ((LDC2_W) handleX).getValue(cpgen);
            } else {
                x = (long) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (long) ((LDC2_W) handleY).getValue(cpgen);
            } else {
                y = (long) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantLong(x - y), cpgen);
        } else if (operand instanceof LMUL) {
            long x;
            long y;
            if (handleX instanceof CPInstruction) {
                x = (long) ((LDC2_W) handleX).getValue(cpgen);
            } else {
                x = (long) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (long) ((LDC2_W) handleY).getValue(cpgen);
            } else {
                y = (long) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantLong(x * y), cpgen);
        } else if (operand instanceof LDIV) {
            long x;
            long y;
            if (handleX instanceof CPInstruction) {
                x = (long) ((LDC2_W) handleX).getValue(cpgen);
            } else {
                x = (long) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (long) ((LDC2_W) handleY).getValue(cpgen);
            } else {
                y = (long) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantLong(x / y), cpgen);
        } else if (operand instanceof DADD) {
            double x;
            double y;
            if (handleX instanceof CPInstruction) {
                x = (double) ((LDC2_W) handleX).getValue(cpgen);
            } else {
                x = (double) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (double) ((LDC2_W) handleY).getValue(cpgen);
            } else {
                y = (double) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantDouble(x + y), cpgen);
        } else if (operand instanceof DSUB) {
            double x;
            double y;
            if (handleX instanceof CPInstruction) {
                x = (double) ((LDC2_W) handleX).getValue(cpgen);
            } else {
                x = (double) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (double) ((LDC2_W) handleY).getValue(cpgen);
            } else {
                y = (double) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantDouble(x - y), cpgen);
        } else if (operand instanceof DMUL) {
            double x;
            double y;
            if (handleX instanceof CPInstruction) {
                x = (double) ((LDC2_W) handleX).getValue(cpgen);
            } else {
                x = (double) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (double) ((LDC2_W) handleY).getValue(cpgen);
            } else {
                y = (double) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantDouble(x * y), cpgen);
        } else if (operand instanceof DDIV) {
            double x;
            double y;
            if (handleX instanceof CPInstruction) {
                x = (double) ((LDC2_W) handleX).getValue(cpgen);
            } else {
                x = (double) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (double) ((LDC2_W) handleY).getValue(cpgen);
            } else {
                y = (double) ((ConstantPushInstruction) handleY).getValue();
            }
            return cpgen.addConstant(new ConstantDouble(x / y), cpgen);
        } else if (operand instanceof LCMP) {
            long x;
            long y;
            if (handleX instanceof CPInstruction) {
                x = (long) ((LDC2_W) handleX).getValue(cpgen);
            } else {
                x = (long) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (long) ((LDC2_W) handleY).getValue(cpgen);
            } else {
                y = (long) ((ConstantPushInstruction) handleY).getValue();
            }
            int ans = Long.compare(x, y);
            return cpgen.addConstant(new ConstantInteger(ans), cpgen);
        } else if (operand instanceof FCMPG || operand instanceof FCMPL) {
            float x;
            float y;
            if (handleX instanceof CPInstruction) {
                x = (float) ((LDC) handleX).getValue(cpgen);
            } else {
                x = (float) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (float) ((LDC) handleY).getValue(cpgen);
            } else {
                y = (float) ((ConstantPushInstruction) handleY).getValue();
            }
            int ans = Float.compare(x, y);
            return cpgen.addConstant(new ConstantInteger(ans), cpgen);
        } else if (operand instanceof DCMPG || operand instanceof DCMPL) {
            double x;
            double y;
            if (handleX instanceof CPInstruction) {
                x = (double) ((LDC2_W) handleX).getValue(cpgen);
            } else {
                x = (double) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (double) ((LDC2_W) handleY).getValue(cpgen);
            } else {
                y = (double) ((ConstantPushInstruction) handleY).getValue();
            }
            int ans = Double.compare(x, y);
            return cpgen.addConstant(new ConstantInteger(ans), cpgen);
        }
        return -1;
    }

    private int unaryOperand(CPInstruction handle, Instruction operand, ConstantPoolGen cpgen) {
        if (operand instanceof I2L) {
            int value = (Integer) ((LDC) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantLong(value), cpgen);
        } else if (operand instanceof I2F) {
            int value = (Integer) ((LDC) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantFloat((float) value), cpgen);
        } else if (operand instanceof I2D) {
            int value = (Integer) ((LDC) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantDouble(value), cpgen);
        } else if (operand instanceof L2I) {
            long value = (Long) ((LDC2_W) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantInteger((int) value), cpgen);
        } else if (operand instanceof L2F) {
            long value = (Long) ((LDC2_W) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantFloat((float) value), cpgen);
        } else if (operand instanceof L2D) {
            long value = (Long) ((LDC2_W) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantDouble((double) value), cpgen);
        } else if (operand instanceof D2F) {
            double value = (Double) ((LDC2_W) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantFloat((float) value), cpgen);
        } else if (operand instanceof D2I) {
            double value = (Double) ((LDC2_W) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantInteger((int) value), cpgen);
        } else if (operand instanceof D2L) {
            double value = (Double) ((LDC2_W) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantLong((long) value), cpgen);
        } else if (operand instanceof F2D) {
            float value = (Float) ((LDC) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantDouble(value), cpgen);
        } else if (operand instanceof F2I) {
            float value = (Float) ((LDC) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantInteger((int) value), cpgen);
        } else if (operand instanceof F2L) {
            float value = (Float) ((LDC) handle).getValue(cpgen);
            return cpgen.addConstant(new ConstantLong((long) value), cpgen);
        }
        return -1;
    }

    private int unaryOperand(ConstantPushInstruction handle, Instruction operand, ConstantPoolGen cpgen) {
        if (operand instanceof I2L || operand instanceof F2L || operand instanceof D2L) {
            return cpgen.addConstant(new ConstantLong((long) handle.getValue()), cpgen);
        } else if (operand instanceof I2F || operand instanceof D2F || operand instanceof L2F) {
            return cpgen.addConstant(new ConstantFloat((float) handle.getValue()), cpgen);
        } else if (operand instanceof I2D || operand instanceof F2D || operand instanceof L2D) {
            return cpgen.addConstant(new ConstantDouble((double) handle.getValue()), cpgen);
        } else if (operand instanceof L2I || operand instanceof F2I || operand instanceof D2I) {
            return cpgen.addConstant(new ConstantInteger((int) handle.getValue()), cpgen);
        }
        return -1;
    }

    private boolean isLoadOp(Instruction instruction) {
        if (instruction instanceof LDC)
            return true;
        if (instruction instanceof LDC2_W)
            return true;
        return instruction instanceof ConstantPushInstruction;
    }

    private void simpleFolding(ConstantPoolGen cpgen, InstructionList instList) {
        InstructionHandle[] handles = instList.getInstructionHandles();
        boolean optimized = false;
        while (!optimized) {
            optimized = true;
            for (int i = 0; i < handles.length; i++) {
                boolean compareInstruction = handles[i].getInstruction() instanceof LCMP
                        || handles[i].getInstruction() instanceof FCMPG || handles[i].getInstruction() instanceof FCMPL
                        || handles[i].getInstruction() instanceof DCMPG || handles[i].getInstruction() instanceof DCMPL;
                if (handles[i].getInstruction() instanceof ArithmeticInstruction || compareInstruction) {
                    Instruction instructionY = handles[i - 1].getInstruction();
                    Instruction instructionX = handles[i - 2].getInstruction();
                    if (instructionY instanceof PushInstruction
                            && !(instructionY instanceof LoadInstruction)
                            && instructionX instanceof PushInstruction
                            && !(instructionX instanceof LoadInstruction)) {
                        Instruction operand = handles[i].getInstruction();
                        int index = calculate((PushInstruction) instructionX,
                                (PushInstruction) instructionY, operand, cpgen);
                        insertVal(instList, handles[i], operand, index);
                        optimized = false;
                        safeDelete(handles[i], instList);
                        safeDelete(handles[i - 1], instList);
                        safeDelete(handles[i - 2], instList);
                        break;
                    }
                }

            }
            handles = instList.getInstructionHandles();
        }
    }

    private void insertVal(InstructionList instList, InstructionHandle handle, Instruction operand, int index) {
        if (operand instanceof IADD || operand instanceof ISUB || operand instanceof IMUL ||
                operand instanceof IDIV || operand instanceof FADD || operand instanceof FSUB
                || operand instanceof FMUL || operand instanceof FDIV || operand instanceof LCMP
                || operand instanceof FCMPL || operand instanceof FCMPG || operand instanceof DCMPL
                || operand instanceof DCMPG) {
            LDC newHandle = new LDC(index);
            instList.insert(handle, newHandle);
            //instList.insert(handle, new LDC(index));
        } else {
            LDC2_W newHandle = new LDC2_W(index);
            instList.insert(handle, newHandle);
            //instList.insert(handle, new LDC2_W(index));
        }
    }

    private Constant newConstant(Object value) {
        if (value instanceof Integer) {
            return new ConstantInteger((int) value);
        } else if (value instanceof Long) {
            return new ConstantLong((long) value);
        } else if (value instanceof Double) {
            return new ConstantDouble((double) value);
        } else if (value instanceof Float) {
            return new ConstantFloat((float) value);
        } else {
            return new ConstantInteger(0);
        }
    }

    private Constant newConstant(Number value) {
        if (value instanceof Integer) {
            return new ConstantInteger((int) value);
        } else if (value instanceof Long) {
            return new ConstantLong((long) value);
        } else if (value instanceof Double) {
            return new ConstantDouble((double) value);
        } else if (value instanceof Float) {
            return new ConstantFloat((float) value);
        } else {
            return new ConstantInteger(0);
        }
    }

    private boolean findLoad(InstructionHandle[] handles, int index, int i) {
        for (int j = 1; j < handles.length; j++) {
            if (j == i) continue;
            if (handles[j].getInstruction() instanceof LoadInstruction
                    && index == ((LoadInstruction) handles[j].getInstruction()).getIndex()) {
                return true;
            }
        }
        return false;
    }

    private boolean findOther(InstructionHandle[] handles, int index, int i) {
        for (int j = 1; j < handles.length; j++) {
            if (j == i) continue;
            if (handles[j].getInstruction() instanceof StoreInstruction
                    && index == ((StoreInstruction) handles[j].getInstruction()).getIndex()) {
                return true;
            }
        }
        return false;
    }

    private void constantPushOptimize(int length, Instruction handle1, InstructionHandle[] handles, int i, int index, ConstantPoolGen cpgen, InstructionList instList) {
        Number value = ((ConstantPushInstruction) handle1).getValue();
        int handleIndex = cpgen.addConstant(newConstant(value), cpgen);
        for (int j = i; j < length; j++) {
            if (handles[j].getInstruction() instanceof LoadInstruction &&
                    ((LoadInstruction) handles[j].getInstruction()).getIndex() == index) {
                if (value instanceof Integer || value instanceof Float) {
                    LDC newHandle = new LDC(handleIndex);
                    instList.insert(handles[j], newHandle);
                } else if (value instanceof Long || value instanceof Double) {
                    LDC2_W newHandle = new LDC2_W(handleIndex);
                    instList.insert(handles[j], newHandle);
                }
                safeDelete(handles[j], instList);
            }
        }
    }

    private void cPOptimize(int length, Instruction handle, InstructionHandle[] handles, int i, int index, InstructionList instList) {
        for (int j = i; j < length; j++) {
            if (handles[j].getInstruction() instanceof LoadInstruction &&
                    ((LoadInstruction) handles[j].getInstruction()).getIndex() == index) {
                if (handle instanceof LDC) {
                    instList.insert(handles[j], new LDC(((LDC) handle).getIndex()));
                } else if (handle instanceof LDC2_W) {
                    instList.insert(handles[j], new LDC2_W(((LDC2_W) handle).getIndex()));
                }
                safeDelete(handles[j], instList);
            }
        }
    }

    private void pushOptimize(InstructionHandle[] handles, int i, int index, ConstantPoolGen cpgen, InstructionList instList) {
        if (handles[i - 1].getInstruction() instanceof ConstantPushInstruction) {
            constantPushOptimize(handles.length, handles[i - 1].getInstruction(), handles, i + 1, index, cpgen, instList);
        } else if (handles[i - 1].getInstruction() instanceof CPInstruction) {
            cPOptimize(handles.length, handles[i - 1].getInstruction(), handles, i + 1, index, instList);
        }
        safeDelete(handles[i], instList);
        safeDelete(handles[i - 1], instList);
    }

    private int getCompareValue(Instruction handle, ConstantPoolGen cpgen) {
        if (handle instanceof ConstantPushInstruction) {
            return (int) ((ConstantPushInstruction) handle).getValue();
        } else {
            return (int) ((LDC) handle).getValue(cpgen);
        }
    }

    private int getCompareX(Instruction instructionX, ConstantPoolGen cpgen) {
        if (instructionX instanceof ConstantPushInstruction)
            return (int) ((ConstantPushInstruction) instructionX).getValue();
        else
            return (int) ((LDC) instructionX).getValue(cpgen);
    }

    private int getCompareY(Instruction instructionY, ConstantPoolGen cpgen) {
        if (instructionY instanceof ConstantPushInstruction)
            return (int) ((ConstantPushInstruction) instructionY).getValue();
        else
            return (int) ((LDC) instructionY).getValue(cpgen);
    }

    private void conversionOptimize(InstructionHandle handle1, InstructionHandle handle2, ConstantPoolGen cpgen, InstructionList instList) {
        int index;
        if (handle1.getInstruction() instanceof CPInstruction) {
            index = unaryOperand((CPInstruction) handle1.getInstruction(), handle2.getInstruction(), cpgen);
        } else {
            index = unaryOperand((ConstantPushInstruction) handle1.getInstruction(), handle2.getInstruction(), cpgen);
        }
        Instruction operand = handle2.getInstruction();
        if (operand instanceof I2L || operand instanceof F2L || operand instanceof D2L || operand instanceof I2D || operand instanceof F2D || operand instanceof L2D) {
            instList.insert(handle2, new LDC2_W(index));
        } else if (operand instanceof I2F || operand instanceof D2F || operand instanceof L2F || operand instanceof L2I || operand instanceof F2I || operand instanceof D2I) {
            instList.insert(handle2, new LDC(index));
        }
        safeDelete(handle1, instList);
        safeDelete(handle2, instList);
    }

    private boolean LDCOrICONST(Instruction inst) {
        if (inst instanceof LDC || inst instanceof ICONST) {
            return true;
        }
        return false;
    }

    private void constantVariablesFolding(ConstantPoolGen cpgen, InstructionList instList) {
        InstructionHandle[] handles = instList.getInstructionHandles();
        boolean optimized = false;
        while (!optimized) {
            optimized = true;
            for (int i = 1; i < handles.length; i++) {
                if (handles[i].getInstruction() instanceof StoreInstruction
                        && handles[i - 1].getInstruction() instanceof PushInstruction) {
                    int index = ((StoreInstruction) handles[i].getInstruction()).getIndex();
                    if (findOther(handles, index, i) && findLoad(handles, index, i))
                        continue;
                    optimized = false;
                    pushOptimize(handles, i, index, cpgen, instList);
                }
                boolean ifUnary = handles[i].getInstruction() instanceof IFEQ || handles[i].getInstruction() instanceof IFLE
                        || handles[i].getInstruction() instanceof IFGT || handles[i].getInstruction() instanceof IFLT
                        || handles[i].getInstruction() instanceof IFNE || handles[i].getInstruction() instanceof IFGE;
                boolean ifBinary = handles[i].getInstruction() instanceof IF_ICMPEQ || handles[i].getInstruction() instanceof IF_ICMPGE
                        || handles[i].getInstruction() instanceof IF_ICMPGT || handles[i].getInstruction() instanceof IF_ICMPLE
                        || handles[i].getInstruction() instanceof IF_ICMPLT || handles[i].getInstruction() instanceof IF_ICMPNE;
                if (ifUnary) {
                    if (isLoadOp(handles[i - 1].getInstruction())) {
                        if (ifUnaryOptimise(handles[i], getCompareValue(handles[i - 1].getInstruction(), cpgen), i, handles, instList)) {
                            optimized = false;
                        }
                        break;
                    }
                }
                if (ifBinary) {
                    if (LDCOrICONST(handles[i - 1].getInstruction()) && LDCOrICONST(handles[i - 2].getInstruction())) {
                        if (ifBinaryOptimise((IfInstruction) handles[i].getInstruction(),
                                getCompareX(handles[i - 2].getInstruction(), cpgen),
                                getCompareY(handles[i - 1].getInstruction(), cpgen), i, handles, instList))
                            optimized = false;
                        break;
                    }
                }
                if (handles[i].getInstruction() instanceof ConversionInstruction) {
                    if (isLoadOp(handles[i - 1].getInstruction())) {
                        optimized = false;
                        conversionOptimize(handles[i - 1], handles[i], cpgen, instList);
                        break;
                    }
                }
            }
            simpleFolding(cpgen, instList);
            handles = instList.getInstructionHandles();
        }
    }

    private int dynamicFoldingHelper(int memoryIndex, int constantIndex, Object value, int StartIndexOfHandles, int EndIndexOfHandles, ConstantPoolGen cpgen, InstructionList instList, boolean isLoop) {
        InstructionHandle[] handles = instList.getInstructionHandles();
        HashMap<Integer, Integer> FromToloop = new HashMap<>();
        HashMap<Integer, Integer> Rangeloop = new HashMap<>();
        int i = StartIndexOfHandles;
        boolean ableToUpdate = true;
        int length = handles.length;
        for (InstructionHandle handle : handles) {
            if (i <= EndIndexOfHandles && i < length) {
                if (handle.getInstruction() instanceof IfInstruction && findTargetIndex(length, handles, ((IfInstruction) handle.getInstruction()).getTarget()) < i) {
                    FromToloop.put(findTargetIndex(length, handles, ((IfInstruction) handle.getInstruction()).getTarget()), i);
                } else if (handles[i].getInstruction() instanceof GotoInstruction && findTargetIndex(length, handles, ((GotoInstruction) handle.getInstruction()).getTarget()) < i) {
                    Rangeloop.put(findTargetIndex(length, handles, ((GotoInstruction) handle.getInstruction()).getTarget()), i);
                }
                if (handle.getInstruction() instanceof StoreInstruction
                        && ((StoreInstruction) handle.getInstruction()).getIndex() == memoryIndex) {
                    ableToUpdate = false;
                }
                i++;
            } else {
                break;
            }
        }
        i = StartIndexOfHandles;
        boolean inLoopRange = false;
        while (i <= EndIndexOfHandles && i < length) {
            if (Rangeloop.containsKey(i))
                inLoopRange = true;
            if (Rangeloop.containsValue(i))
                inLoopRange = false;
            if (!inLoopRange && (!isLoop || ableToUpdate) && value != null && handles[i].getInstruction() instanceof LoadInstruction
                    && ((LoadInstruction) handles[i].getInstruction()).getIndex() == memoryIndex) {
                isOptimised = false;
                if (value instanceof Integer || value instanceof Float) {
                    LDC newHandle = new LDC(constantIndex);
                    instList.insert(handles[i], newHandle);
                } else if (value instanceof Long || value instanceof Double) {
                    LDC2_W newHandle = new LDC2_W(constantIndex);
                    instList.insert(handles[i], newHandle);
                }
                safeDelete(handles[i], instList);
            }
            if (handles[i].getInstruction() instanceof StoreInstruction && ((StoreInstruction) handles[i].getInstruction()).getIndex() == memoryIndex) {
                Instruction in = handles[i-1].getInstruction();
                if (in instanceof PushInstruction && !(in instanceof LoadInstruction)) {
                    ableToUpdate = true;
                    if (in instanceof ConstantPushInstruction) {
                        value = ((ConstantPushInstruction) in).getValue();
                        constantIndex = cpgen.addConstant(newConstant(value), cpgen);
                    } else if (in instanceof CPInstruction) {
                        if ((in) instanceof LDC) {
                            constantIndex = ((LDC) in).getIndex();
                            value = ((LDC) in).getValue(cpgen);
                        } else if ((in) instanceof LDC2_W) {
                            constantIndex = ((LDC2_W) in).getIndex();
                            value = ((LDC2_W) in).getValue(cpgen);
                        }
                    }
                } else {
                    value = null;
                    constantIndex = -1;
                }
            }

            if (handles[i].getInstruction() instanceof GotoInstruction
                    || handles[i].getInstruction() instanceof ReturnInstruction
                    || (handles[i].getInstruction() instanceof IfInstruction && findTargetIndex(length, handles, ((IfInstruction) handles[i].getInstruction()).getTarget()) < i)) {
                return constantIndex;
            }


            //Attention
            if (handles[i].getInstruction() instanceof IfInstruction
                    && findTargetIndex(length, handles, ((IfInstruction) handles[i].getInstruction()).getTarget()) > i) {
                InstructionHandle targetInstruction = ((IfInstruction) handles[i].getInstruction()).getTarget();
                int ifToPosition = findTargetIndex(length, handles, ((IfInstruction) handles[i].getInstruction()).getTarget());
                if (handles[ifToPosition - 1].getInstruction() instanceof GotoInstruction) {
                    // there is goto before if's target
                    InstructionHandle gotoInstruction = handles[ifToPosition - 1];
                    InstructionHandle goToTarget = ((GotoInstruction) gotoInstruction.getInstruction()).getTarget();
                    if (findTargetIndex(length, handles, goToTarget) < ifToPosition - 1) {
                        // goto upwards : while or for loop
                        int goToPosition = findTargetIndex(length, handles, goToTarget);
                        constantIndex = dynamicFoldingHelper(memoryIndex, constantIndex, value, i + 1, ifToPosition - 2, cpgen, instList, true);
                        i = ifToPosition - 1;
                        inLoopRange = false;
                        if (constantIndex == -1) {
                            constantIndex = -1;
                            value = null;
                        }

                    } else {
                        int goToPosition = findTargetIndex(length, handles, goToTarget);
                        int index1 = dynamicFoldingHelper(memoryIndex, constantIndex, value, i + 1, ifToPosition - 2, cpgen, instList, isLoop);
                        int index2 = dynamicFoldingHelper(memoryIndex, constantIndex, value, ifToPosition, goToPosition - 1, cpgen, instList, isLoop);
                        i = goToPosition - 1;
                        if ((index1 != -1 && index2 != -1 && index1 == index2)) {
                        } else {
                            value = null;
                            constantIndex = -1;
                        }
                    }
                } else {
                    constantIndex = dynamicFoldingHelper(memoryIndex, constantIndex, value, i + 1, ifToPosition - 1, cpgen, instList, isLoop);
                    i = ifToPosition - 1;
                    if (constantIndex == -1) {
                        constantIndex = -1;
                        value = null;
                    }
                }
            }

            if (FromToloop.containsKey(i)) {
                constantIndex = dynamicFoldingHelper(memoryIndex, constantIndex, value, i, FromToloop.get(i) - 1, cpgen, instList, true);
                i = FromToloop.get(i) - 1;
                inLoopRange = false;
                if (constantIndex == -1) {
                    constantIndex = -1;
                    value = null;
                }
            }

            i++;
        }
        return constantIndex;
    }

    private void dynamicVariablesFolding(ConstantPoolGen cpgen, InstructionList instList) {
        InstructionHandle[] handles = instList.getInstructionHandles();
        ConstantPool cp = cpgen.getConstantPool();
        HashSet<Integer> variableSet = new HashSet<>();
        for (InstructionHandle handle : handles) {
            if (handle.getInstruction() instanceof StoreInstruction) {
                if (!variableSet.contains(((StoreInstruction) handle.getInstruction()).getIndex())) {
                    variableSet.add(((StoreInstruction) handle.getInstruction()).getIndex());
                }
            }
        }
        do {
            isOptimised = true;
            for (int i : variableSet) {
                handles = instList.getInstructionHandles();
                dynamicFoldingHelper(i, -1, null, 0, handles.length - 1, cpgen, instList, false);
            }
            constantVariablesFolding(cpgen, instList);
        } while (!isOptimised);
    }

    private void optimizeMethod(ConstantPoolGen cpgen, ClassGen cgen, Method method) {
        Code methodCode = method.getCode();
        InstructionList instList = new InstructionList(methodCode.getCode());
        MethodGen methodGen = new MethodGen(method.getAccessFlags(), method.getReturnType(), method.getArgumentTypes(),
                null, method.getName(), cgen.getClassName(), instList, cpgen);
        methodGen.removeNOPs();

        simpleFolding(cpgen, instList);
        constantVariablesFolding(cpgen, instList);
        dynamicVariablesFolding(cpgen, instList);

        methodGen.stripAttributes(true);
        methodGen.setMaxStack();

        Method newMethod = methodGen.getMethod();
        this.gen.replaceMethod(method, newMethod);
        this.gen.setConstantPool(cpgen);
        this.gen.setMajor(50);

    }

    public void optimize() {
        ClassGen cgen = new ClassGen(original);
        ConstantPoolGen cpgen = cgen.getConstantPool();
        Method[] methods = cgen.getMethods();
        for (Method method : methods) {
            optimizeMethod(cpgen, cgen, method);
        }
        this.optimized = gen.getJavaClass();
    }

    public void write(String optimisedFilePath) {
        this.optimize();

        try {
            FileOutputStream out = new FileOutputStream(new File(optimisedFilePath));
            this.optimized.dump(out);
        } catch (FileNotFoundException e) {
            // Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
    }
}