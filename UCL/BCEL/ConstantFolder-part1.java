package comp0012.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.bcel.util.InstructionFinder;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;



public class ConstantFolder
{
	ClassParser parser = null;
	ClassGen gen = null;

	JavaClass original = null;
	JavaClass optimized = null;

	public ConstantFolder(String classFilePath)
	{
		try{
			this.parser = new ClassParser(classFilePath);
			this.original = this.parser.parse();
			this.gen = new ClassGen(this.original);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	private void simpleFolding(ConstantPoolGen cpgen, InstructionList instList) {
		InstructionHandle[] handles = instList.getInstructionHandles();
		ConstantPool cp = cpgen.getConstantPool();
		for (int i = 0; i < handles.length; i++)
		{
			if (handles[i].getInstruction() instanceof ArithmeticInstruction)
			{
				if (handles[i - 1].getInstruction() instanceof PushInstruction
						&& handles[i - 2].getInstruction() instanceof PushInstruction
						&& !(handles[i - 1].getInstruction() instanceof LoadInstruction)
						&& !(handles[i - 2].getInstruction() instanceof LoadInstruction))
				{
					Instruction operand = handles[i].getInstruction();
					int index = calc((PushInstruction) handles[i - 2].getInstruction(),
							(PushInstruction) handles[i - 1].getInstruction(), operand, cpgen);
					if (operand instanceof IADD || operand instanceof ISUB || operand instanceof IMUL ||
							operand instanceof IDIV || operand instanceof FADD || operand instanceof FSUB
							|| operand instanceof FMUL || operand instanceof FDIV)
					{
						instList.insert(handles[i], new LDC(index));
					} else {
						instList.insert(handles[i], new LDC2_W(index));
					}
					try {
						instList.delete(handles[i]);
						instList.delete(handles[i - 1]);
						instList.delete(handles[i - 2]);
					} catch (TargetLostException e) {

					}
				}
			}
		}
	}

	private Number loadDataValue(LDC2_W handle, ConstantPoolGen cpgen) {
		return handle.getValue(cpgen);
	}

	private Object loadDataValue(LDC handle, ConstantPoolGen cpgen) {
		return handle.getValue(cpgen);
	}

	private int calc(PushInstruction handleX, PushInstruction handleY, Instruction operand, ConstantPoolGen cpgen) {
		int index = -1;
		if (operand instanceof IADD) {
			int x = (int) loadDataValue((LDC) handleX, cpgen);
			int y = (int) loadDataValue((LDC) handleY, cpgen);
			int ans = x + y;
			index = cpgen.addConstant(new ConstantInteger(ans), cpgen);
		} else if (operand instanceof ISUB) {
			int x = (int) loadDataValue((LDC) handleX, cpgen);
			int y = (int) loadDataValue((LDC) handleY, cpgen);
			int ans = x - y;
			index = cpgen.addConstant(new ConstantInteger(ans), cpgen);
		} else if (operand instanceof IMUL) {
			int x = (int) loadDataValue((LDC) handleX, cpgen);
			int y = (int) loadDataValue((LDC) handleY, cpgen);
			int ans = x * y;
			index = cpgen.addConstant(new ConstantInteger(ans), cpgen);
		} else if (operand instanceof IDIV) {
			int x = (int) loadDataValue((LDC) handleX, cpgen);
			int y = (int) loadDataValue((LDC) handleY, cpgen);
			int ans = x / y;
			index = cpgen.addConstant(new ConstantInteger(ans), cpgen);
		} else if (operand instanceof FADD) {
			float x = (float) loadDataValue((LDC) handleX, cpgen);
			float y = (float) loadDataValue((LDC) handleY, cpgen);
			float ans = x + y;
			index = cpgen.addConstant(new ConstantFloat(ans), cpgen);
		} else if (operand instanceof FSUB) {
			float x = (float) loadDataValue((LDC) handleX, cpgen);
			float y = (float) loadDataValue((LDC) handleY, cpgen);
			float ans = x - y;
			index = cpgen.addConstant(new ConstantFloat(ans), cpgen);
		} else if (operand instanceof FMUL) {
			float x = (float) loadDataValue((LDC) handleX, cpgen);
			float y = (float) loadDataValue((LDC) handleY, cpgen);
			float ans = x * y;
			index = cpgen.addConstant(new ConstantFloat(ans), cpgen);
		} else if (operand instanceof FDIV) {
			float x = (float) loadDataValue((LDC) handleX, cpgen);
			float y = (float) loadDataValue((LDC) handleY, cpgen);
			float ans = x / y;
			index = cpgen.addConstant(new ConstantFloat(ans), cpgen);
		} else if (operand instanceof LADD) {
			long x = (long) loadDataValue((LDC2_W) handleX, cpgen);
			long y = (long) loadDataValue((LDC2_W) handleY, cpgen);
			long ans = x + y;
			index = cpgen.addConstant(new ConstantLong(ans), cpgen);
		} else if (operand instanceof LSUB) {
			long x = (long) loadDataValue((LDC2_W) handleX, cpgen);
			long y = (long) loadDataValue((LDC2_W) handleY, cpgen);
			long ans = x - y;
			index = cpgen.addConstant(new ConstantLong(ans), cpgen);
		} else if (operand instanceof LMUL) {
			long x = (long) loadDataValue((LDC2_W) handleX, cpgen);
			long y = (long) loadDataValue((LDC2_W) handleY, cpgen);
			long ans = x * y;
			index = cpgen.addConstant(new ConstantLong(ans), cpgen);
		} else if (operand instanceof LDIV) {
			long x = (long) loadDataValue((LDC2_W) handleX, cpgen);
			long y = (long) loadDataValue((LDC2_W) handleY, cpgen);
			long ans = x / y;
			index = cpgen.addConstant(new ConstantLong(ans), cpgen);
		} else if (operand instanceof DADD) {
			double x = (double) loadDataValue((LDC2_W) handleX, cpgen);
			double y = (double) loadDataValue((LDC2_W) handleY, cpgen);
			double ans = x + y;
			index = cpgen.addConstant(new ConstantDouble(ans), cpgen);
		} else if (operand instanceof DSUB) {
			double x = (double) loadDataValue((LDC2_W) handleX, cpgen);
			double y = (double) loadDataValue((LDC2_W) handleY, cpgen);
			double ans = x - y;
			index = cpgen.addConstant(new ConstantDouble(ans), cpgen);
		} else if (operand instanceof DMUL) {
			double x = (double) loadDataValue((LDC2_W) handleX, cpgen);
			double y = (double) loadDataValue((LDC2_W) handleY, cpgen);
			double ans = x * y;
			index = cpgen.addConstant(new ConstantDouble(ans), cpgen);
		} else if (operand instanceof DDIV) {
			double x = (double) loadDataValue((LDC2_W) handleX, cpgen);
			double y = (double) loadDataValue((LDC2_W) handleY, cpgen);
			double ans = x / y;
			index = cpgen.addConstant(new ConstantDouble(ans), cpgen);
		}
		return index;
	}

	private Method optimizeMethod(ConstantPoolGen cpgen, ClassGen cgen, Method m) {
		MethodGen mg = new MethodGen(m, cgen.getClassName(), cpgen);
		mg.removeNOPs();
		InstructionList instList = mg.getInstructionList();

		simpleFolding(cpgen, instList);

		mg.stripAttributes(true);
		mg.setMaxStack();
		return mg.getMethod();
	}

	public void optimize() {
		ClassGen cgen = new ClassGen(original);
		ConstantPoolGen cpgen = cgen.getConstantPool();
		Method[] methods = cgen.getMethods();
		Method[] optimizedMethods = new Method[methods.length];
		for (int i = 0; i < methods.length; i++) {
			optimizedMethods[i] = optimizeMethod(cpgen, cgen, methods[i]);
		}
		this.gen.setMethods(optimizedMethods);
		this.gen.setConstantPool(cpgen);
		this.gen.setMajor(50);
		this.optimized = gen.getJavaClass();
	}

	public void write(String optimisedFilePath)
	{
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
