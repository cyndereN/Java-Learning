import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AppGraphInOut
{
	public static void main( String args[] )
	{
		new AppFrame();
	}
}

class AppFrame extends JFrame
{
	JTextField in = new JTextField(10);
	JButton btn = new JButton("Get the square");
	JLabel out = new JLabel("Label to show the result");

	public AppFrame()
	{
		setLayout( new FlowLayout() );
		getContentPane().add( in );
		getContentPane().add( btn );
		getContentPane().add( out );
		btn.addActionListener( new BtnActionAdapter() );
		setSize( 400,100 );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	class BtnActionAdapter implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
			String s = in.getText();
			double d = Double.parseDouble( s );
			double sq = d * d;
			out.setText( d + "'s square root is" + sq );
		}
	}
}
