//Jonathan Lyttle
//netid: jbl160530
//Driver for Calculator

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class CalcTest 
{
	public static void main(String[] args)
	{
		try 
		{
			 for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
			 {
			        if ("Nimbus".equals(info.getName())) 
			        {
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }		
			 }
	    }
		catch (Exception e) 
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		Calculator window = new Calculator();
		window.setSize(425, 405);
		window.setVisible(true);
		window.setTitle("Calculator");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
	}
}
