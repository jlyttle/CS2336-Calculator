import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CalcTest 
{
	public static void main(String[] args)
	{
		try 
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calculator window = new Calculator();
		window.setSize(500, 500);
		window.setVisible(true);
		window.setTitle("Calculator");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		//window.pack();
	}
}
