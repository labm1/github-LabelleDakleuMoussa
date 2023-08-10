package timelog;


public interface PayrollInterface {
	abstract public double netFrombrute(PayInfo info);
	abstract public double deduction();
	abstract public void printPay(PayInfo info);
}
