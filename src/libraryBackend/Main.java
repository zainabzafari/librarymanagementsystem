package libraryBackend;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import libraryFrontend.BookManagementPanel;
import libraryFrontend.IssueBook;
import libraryFrontend.MemberManagementPanel;
import libraryFrontend.MemberPanel;
import libraryFrontend.ReturnBook;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Book Management Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            BookManagementPanel bookManagementPanel = new BookManagementPanel();
            
            //frame.add(bookManagementPanel);
            //MemberPanel panel = new MemberPanel();
            //frame.add(panel);
            MemberPanel userpanel = new MemberPanel();
            //frame.add(userpanel);
            IssueBook issuebook = new IssueBook();
            ReturnBook returnBook = new ReturnBook();
            frame.add(returnBook);
            frame.setVisible(true);
           // MemberManagementPanel memberpanel = new MemberManagementPanel();
            //frame.add(memberpanel);
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);
        });
    }
}
