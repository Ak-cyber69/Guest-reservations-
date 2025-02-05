import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GuestReservationSystem extends JFrame {
   
    private List<Reservation> reservations;
    
    private JTextField guestNameField, phoneNumberField, checkInDateField, checkOutDateField;
    private JTextArea reservationArea;
    private JButton createButton, updateButton, deleteButton, readButton;
    private int currentIndex = -1; 

    public GuestReservationSystem() {
        reservations = new ArrayList<>();
        
        setTitle("Guest Reservation System with CRUD");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

       
        JLabel guestNameLabel = new JLabel("Guest Name:");
        guestNameLabel.setBounds(20, 20, 100, 25);
        add(guestNameLabel);
        
        guestNameField = new JTextField();
        guestNameField.setBounds(130, 20, 200, 25);
        add(guestNameField);
        
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setBounds(20, 60, 100, 25);
        add(phoneNumberLabel);
        
        phoneNumberField = new JTextField();
        phoneNumberField.setBounds(130, 60, 200, 25);
        add(phoneNumberField);
        
        JLabel checkInDateLabel = new JLabel("Check-in Date:");
        checkInDateLabel.setBounds(20, 100, 100, 25);
        add(checkInDateLabel);
        
        checkInDateField = new JTextField();
        checkInDateField.setBounds(130, 100, 200, 25);
        add(checkInDateField);
        
        JLabel checkOutDateLabel = new JLabel("Check-out Date:");
        checkOutDateLabel.setBounds(20, 140, 100, 25);
        add(checkOutDateLabel);
        
        checkOutDateField = new JTextField();
        checkOutDateField.setBounds(130, 140, 200, 25);
        add(checkOutDateField);
        
     
        createButton = new JButton("Create");
        createButton.setBounds(20, 180, 80, 25);
        add(createButton);
        
        readButton = new JButton("Read");
        readButton.setBounds(110, 180, 80, 25);
        add(readButton);
        
        updateButton = new JButton("Update");
        updateButton.setBounds(200, 180, 80, 25);
        add(updateButton);
        
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(290, 180, 80, 25);
        add(deleteButton);

       
        reservationArea = new JTextArea();
        reservationArea.setBounds(20, 220, 440, 300);
        reservationArea.setEditable(false);
        add(reservationArea);
        
      
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReservation();
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayReservations();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateReservation();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteReservation();
            }
        });
        
        reservationArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                selectReservation(evt);
            }
        });
    }
    
    private void createReservation() {
        String guestName = guestNameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String checkInDate = checkInDateField.getText();
        String checkOutDate = checkOutDateField.getText();
        
        if (validateFields(guestName, phoneNumber, checkInDate, checkOutDate)) {
            reservations.add(new Reservation(guestName, phoneNumber, checkInDate, checkOutDate));
            clearFields();
            JOptionPane.showMessageDialog(null, "Reservation Created!");
        } else {
            JOptionPane.showMessageDialog(null, "Please fill all fields.");
        }
    }
    
    private void displayReservations() {
        reservationArea.setText(""); 
        int i = 0;
        for (Reservation reservation : reservations) {
            reservationArea.append("ID: " + i++ + " | Name: " + reservation.getGuestName() + 
                                   " | Phone: " + reservation.getPhoneNumber() +
                                   " | Check-in: " + reservation.getCheckInDate() +
                                   " | Check-out: " + reservation.getCheckOutDate() + "\n");
        }
    }

    private void updateReservation() {
        if (currentIndex >= 0 && currentIndex < reservations.size()) {
            String guestName = guestNameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String checkInDate = checkInDateField.getText();
            String checkOutDate = checkOutDateField.getText();
            
            if (validateFields(guestName, phoneNumber, checkInDate, checkOutDate)) {
                Reservation res = reservations.get(currentIndex);
                res.setGuestName(guestName);
                res.setPhoneNumber(phoneNumber);
                res.setCheckInDate(checkInDate);
                res.setCheckOutDate(checkOutDate);
                
                clearFields();
                displayReservations();
                JOptionPane.showMessageDialog(null, "Reservation Updated!");
            } else {
                JOptionPane.showMessageDialog(null, "Please fill all fields.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No reservation selected.");
        }
    }
    
    private void deleteReservation() {
        if (currentIndex >= 0 && currentIndex < reservations.size()) {
            reservations.remove(currentIndex);
            clearFields();
            displayReservations();
            JOptionPane.showMessageDialog(null, "Reservation Deleted!");
        } else {
            JOptionPane.showMessageDialog(null, "No reservation selected.");
        }
    }
    
    private void selectReservation(MouseEvent evt) {
        String selectedText = reservationArea.getSelectedText();
        if (selectedText != null) {
            String[] parts = selectedText.split(" ");
            currentIndex = Integer.parseInt(parts[1]);
            
            Reservation selectedReservation = reservations.get(currentIndex);
            guestNameField.setText(selectedReservation.getGuestName());
            phoneNumberField.setText(selectedReservation.getPhoneNumber());
            checkInDateField.setText(selectedReservation.getCheckInDate());
            checkOutDateField.setText(selectedReservation.getCheckOutDate());
        }
    }

    private boolean validateFields(String guestName, String phoneNumber, String checkInDate, String checkOutDate) {
        return !guestName.isEmpty() && !phoneNumber.isEmpty() && !checkInDate.isEmpty() && !checkOutDate.isEmpty();
    }

    private void clearFields() {
        guestNameField.setText("");
        phoneNumberField.setText("");
        checkInDateField.setText("");
        checkOutDateField.setText("");
        currentIndex = -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuestReservationSystem reservationSystem = new GuestReservationSystem();
            reservationSystem.setVisible(true);
        });
    }
}


class Reservation {
    private String guestName;
    private String phoneNumber;
    private String checkInDate;
    private String checkOutDate;

    public Reservation(String guestName, String phoneNumber, String checkInDate, String checkOutDate) {
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
