	



	package cn.march.analysis.model.akka;
	
	
	
	import java.io.Serializable;
	
	/**
	 * 数据计算实体类(需实现序列化接口：因为经过远程传输调用(二进制形式))
	 * 
	 * @author antsmarth
	 *
	 */
	public class Op {
	

		//操作数据类接口：Add Sub Mul Div
		public interface MathOp extends Serializable {
		}
		
		//计算结果类：Add Sub Mul Div
		public interface MathResult extends Serializable {
		}
	
		//Add操作数据类
		static class Add implements MathOp {
			private static final long serialVersionUID = 1L;
			private final int n1;
			private final int n2;
	
			public Add(int n1, int n2) {
				this.n1 = n1;
				this.n2 = n2;
			}
	
			public int getN1() {
				return n1;
			}
	
			public int getN2() {
				return n2;
			}
		}
		
		//Add计算结果
		static class AddResult implements MathResult {
	
			private static final long serialVersionUID = 1L;
			private final int n1;
			private final int n2;
			private final int result;
	
			public AddResult(int n1, int n2, int result) {
				this.n1 = n1;
				this.n2 = n2;
				this.result = result;
			}
	
			public int getN1() {
				return n1;
			}
	
			public int getN2() {
				return n2;
			}
	
			public int getResult() {
				return result;
			}
		}
		
		//Sub操作
		static class Subtract implements MathOp {
			private static final long serialVersionUID = 1L;
			private final int n1;
			private final int n2;
	
			public Subtract(int n1, int n2) {
				this.n1 = n1;
				this.n2 = n2;
			}
	
			public int getN1() {
				return n1;
			}
	
			public int getN2() {
				return n2;
			}
		}
	
		//Sub计算结果
		static class SubtractResult implements MathResult {
			private static final long serialVersionUID = 1L;
			private final int n1;
			private final int n2;
			private final int result;
	
			public SubtractResult(int n1, int n2, int result) {
				this.n1 = n1;
				this.n2 = n2;
				this.result = result;
			}
	
			public int getN1() {
				return n1;
			}
	
			public int getN2() {
				return n2;
			}
	
			public int getResult() {
				return result;
			}
		}
	
		//Mul操作
		static class Multiply implements MathOp {
			private static final long serialVersionUID = 1L;
			private final int n1;
			private final int n2;
	
			public Multiply(int n1, int n2) {
				this.n1 = n1;
				this.n2 = n2;
			}
	
			public int getN1() {
				return n1;
			}
	
			public int getN2() {
				return n2;
			}
		}
	
		//Mul结果
		static class MultiplicationResult implements MathResult {
			private static final long serialVersionUID = 1L;
			private final int n1;
			private final int n2;
			private final int result;
	
			public MultiplicationResult(int n1, int n2, int result) {
				this.n1 = n1;
				this.n2 = n2;
				this.result = result;
			}
	
			public int getN1() {
				return n1;
			}
	
			public int getN2() {
				return n2;
			}
	
			public int getResult() {
				return result;
			}
		}
	
		//Div操作
		static class Divide implements MathOp {
			private static final long serialVersionUID = 1L;
			private final double n1;
			private final int n2;
	
			public Divide(double n1, int n2) {
				this.n1 = n1;
				this.n2 = n2;
			}
	
			public double getN1() {
				return n1;
			}
	
			public int getN2() {
				return n2;
			}
		}
	
		//Div结果
		static class DivisionResult implements MathResult {
			private static final long serialVersionUID = 1L;
			private final double n1;
			private final int n2;
			private final double result;
	
			public DivisionResult(double n1, int n2, double result) {
				this.n1 = n1;
				this.n2 = n2;
				this.result = result;
			}
	
			public double getN1() {
				return n1;
			}
	
			public int getN2() {
				return n2;
			}
	
			public double getResult() {
				return result;
			}
		}
	}
