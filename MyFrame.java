package chatApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MyFrame extends JFrame implements ActionListener,KeyListener, ListSelectionListener {
	
	JList<String> contactList;
	DefaultListModel<String> contactListModel;
	JScrollPane contactScrollPane;
	JSplitPane mainPane;
	JTextArea chatHistory;
	JTextField messageBox;
	JLabel cName;
	Map<String,String> chatHistories;
	String currentContact;
	
	
	MyFrame(){
		setTitle("ChatApp");
		Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(d.getWidth()/2),(int)(d.getHeight()/2));
		
		//Initialize chat history
		chatHistories=new HashMap<>();
		
		//contact list panel
		contactListModel = new DefaultListModel<>();
		for(int i=1;i<=50;i++) {
			contactListModel.addElement(" Friend Name "+i);
		}
		contactList = new JList<>(contactListModel);
		contactList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane contactPane = new JScrollPane(contactList);
        contactList.addListSelectionListener(this);
        
        //contact Label
        JLabel cLabel= new JLabel("Contacts");
        cLabel.setBorder(new EmptyBorder(5,0,5,0));
        cLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        cLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel contactPanel= new JPanel(new BorderLayout());
        contactPanel.setBackground(new Color(190, 173, 250));
        contactPanel.add(cLabel, BorderLayout.NORTH);
        contactPanel.add(contactPane,BorderLayout.CENTER );
        
        
        //chat history panel
        chatHistory = new JTextArea();
        chatHistory.setText("  Welcome to the Chat Application!\n\n" +
                "  Features and Functionality:\n\n" +
                "  1. Contact List: View and select contacts from a list to start chatting.\n\n" +
                "  2. Chat History: View the chat history with the selected contact. Previous messages are displayed for continuity.\n\n" +
                "  3. Send Messages: Type your messages in the input field and press Enter or click the 'Send' button to send messages.\n\n" +
                "  4. Responsive UI: The application automatically adjusts the layout and components for a seamless user experience.\n\n" +
                "  5. Empty Text Warning: A Warning pop up will be seen if an empty  message is sent.\n\n" +
                "  Enjoy chatting!");
        chatHistory.setFont(new Font("Arial", Font.PLAIN, 14));
        chatHistory.setEditable(false);
        JScrollPane chatPane = new JScrollPane(chatHistory);
        
        //Contact name Label
        cName= new JLabel("Select Contact for Chatting");
        cName.setBorder(new EmptyBorder(5,0,5,0));
        cName.setFont(new Font("Arial", Font.PLAIN, 18));
        cName.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(190, 173, 250));
        topPanel.add(cName, BorderLayout.NORTH);
        topPanel.add(chatPane,BorderLayout.CENTER );
        
        
        //Setting the contact list and chat history in a split pane
        mainPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,topPanel,contactPanel);
        mainPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Calculate the new divider location based on the frame's width
                int newDividerLocation = getWidth()*4/5; // Example: 1/3 of the frame width
                mainPane.setDividerLocation(newDividerLocation);
            }
        });
        mainPane.setDividerLocation(640);
        mainPane.disable();
        
        //Message panel
        messageBox= new JTextField(35);
        JButton sendBtn= new JButton("SEND");
        sendBtn.setBackground(new Color(255, 248, 201));  //231	307	345
        sendBtn.addActionListener(this);
        messageBox.addKeyListener(this);
        
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(new EmptyBorder(7,7,7,7));
        messagePanel.setBackground(new Color(190, 173, 250));
        messagePanel.add(messageBox, BorderLayout.CENTER);
        messagePanel.add(sendBtn, BorderLayout.EAST);
        
        
        
        //Adding components to the main frame
        add(mainPane,BorderLayout.CENTER);
        add(messagePanel,BorderLayout.SOUTH);
        
	}


	//Message Send Function
	@Override
	public void actionPerformed(ActionEvent e) {
		sendMessage();
		
	}
	  private void sendMessage() {
      	String msg= messageBox.getText();
      	if(!msg.trim().isEmpty()) {
      		String history= chatHistories.getOrDefault(currentContact, "");
      		history += "Me: " + msg + "\n";
      		chatHistories.put(currentContact,history);
      		chatHistory.append("Me: " + msg + "\n");
      		messageBox.setText("");
      		}
      	else {
      		JOptionPane.showMessageDialog(this, "Cannot Send an Empty Message", "Warning",JOptionPane.WARNING_MESSAGE);
      	}
      	
      	}
	  
	  private void loadChatHistory(String contact) {
	        chatHistory.setText(chatHistories.getOrDefault(contact, ""));
	    }


	
	  //Message Send using enter key
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            sendMessage();
        } 
			
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// NOT USED
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// NOT USED
		
	}


	//Label set for contact chat from contact list
	@Override
	public void valueChanged(ListSelectionEvent e) {
		String selectedContact=contactList.getSelectedValue();
		if(!selectedContact.isEmpty()) {
			cName.setText(selectedContact);
			chatHistory.setText("");
			currentContact = selectedContact;
			loadChatHistory(selectedContact);
			
		}
		
	}



	
	

}
