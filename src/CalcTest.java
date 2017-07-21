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
