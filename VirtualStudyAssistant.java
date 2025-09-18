import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class VirtualStudyAssistant {

    private static JFrame frame;
    private static String[] tasks = new String[10];
    private static int taskCount = 0;
    private static JLabel timerLabel;
    private static JLabel blankLabel; // This is the blank label after the timer
    private static JLabel whiteLabel; // This is the white label after the timer starts
    private static Timer countdownTimer;
    private static int remainingTime;
    private static int studyMinutes = 25; // Default study time
    private static int breakMinutes = 5;  // Default break time

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Virtual Study Assistant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Set a background image
        JPanel backgroundPanel = new JPanel() {
            Image backgroundImage = new ImageIcon("girl.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 50, getWidth(), getHeight(), this); // Stretch image to cover the panel
            }
        };

        // Add the logo and title at the top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Virtual Study Assistant", JLabel.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        titleLabel.setForeground(Color.decode("#8A2BE2"));

        // Image below the title
        ImageIcon logoIcon = new ImageIcon("path/to/your/logo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add both title and logo below it to the topPanel
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(logoLabel, BorderLayout.CENTER); // Add the logo below the title

        // Timer label to display countdown
        timerLabel = new JLabel("00:00", JLabel.CENTER);
        timerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        timerLabel.setForeground(Color.decode("#8A2BE2"));
        timerLabel.setVisible(false); // Initially, the timer is hidden

        // Blank label after the timer
        blankLabel = new JLabel(" "); // Blank label
        blankLabel.setPreferredSize(new Dimension(0, 20)); // Set a size if needed
        blankLabel.setVisible(true); // Initially visible for spacing

        // White label below timer after timer starts
        whiteLabel = new JLabel("Timer Started", JLabel.CENTER);
        whiteLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        whiteLabel.setForeground(Color.BLACK);
        whiteLabel.setOpaque(true);
        whiteLabel.setBackground(Color.WHITE);
        whiteLabel.setPreferredSize(new Dimension(600, 40)); // Size for the label
        whiteLabel.setVisible(false); // Initially hidden until the timer starts

        // Create buttons with icons
        JButton viewButton = createButton("View Tasks", "path/to/view_icon.png");
        JButton addButton = createButton("Add Task", "path/to/add_icon.png");
        JButton deleteButton = createButton("Delete Task", "path/to/delete_icon.png");
        JButton timerButton = createButton("Start Pomodoro", "path/to/timer_icon.png");
        JButton quoteButton = createButton("Motivational Quote", "path/to/quote_icon.png");
        JButton exitButton = createButton("Exit", "path/to/exit_icon.png");

        // Add ActionListeners to buttons
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewTasks();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });

        timerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTimerAndStartPomodoro();  // Set custom timer and start Pomodoro
            }
        });

        quoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMotivationalQuote();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Create a panel for buttons and set layout using GridBagLayout
        JPanel buttonPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        buttonPanel.setLayout(gridBagLayout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        buttonPanel.add(viewButton, gbc);

        gbc.gridx = 1;
        buttonPanel.add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        buttonPanel.add(timerButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonPanel.add(quoteButton, gbc);

        gbc.gridx = 1;
        buttonPanel.add(exitButton, gbc);

        // Add the top panel, timer label, blank label, white label, and button panel to the frame
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(timerLabel, BorderLayout.CENTER);  // Add timer label to the center
        backgroundPanel.add(blankLabel, BorderLayout.SOUTH);  // Add the blank label after the timer
        backgroundPanel.add(whiteLabel, BorderLayout.SOUTH);  // Add the white label below the timer
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(backgroundPanel); // Set background panel as the content pane
        frame.setVisible(true);
    }

    // Helper function to create buttons with icons
    private static JButton createButton(String text, String iconPath) {
        ImageIcon icon = new ImageIcon(iconPath);
        final JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        button.setBackground(Color.decode("#8A2BE2"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 55));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);

        // Hover effect (using mouse listeners)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#D8BFD8"));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#8A2BE2"));
            }
        });

        return button;
    }

    // Task Management Section
    private static void viewTasks() {
        String taskList = "";
        if (taskCount == 0) {
            taskList = "No tasks available.";
        } else {
            for (int i = 0; i < taskCount; i++) {
                taskList += (i + 1) + ". " + tasks[i] + "\n";
            }
        }
        JOptionPane.showMessageDialog(frame, taskList, "Your Tasks", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void addTask() {
        String task = JOptionPane.showInputDialog(frame, "Enter a new task:");
        if (task != null && !task.isEmpty()) {
            if (taskCount == tasks.length) {
                tasks = java.util.Arrays.copyOf(tasks, tasks.length * 2);
            }
            tasks[taskCount++] = task;
            JOptionPane.showMessageDialog(frame, "Task added successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a valid task.");
        }
    }

    private static void deleteTask() {
        String taskList = "";
        if (taskCount == 0) {
            taskList = "No tasks available.";
        } else {
            for (int i = 0; i < taskCount; i++) {
                taskList += (i + 1) + ". " + tasks[i] + "\n";
            }
        }

        String taskToDelete = JOptionPane.showInputDialog(frame, "Enter the task number to delete:\n" + taskList);
        try {
            int taskIndex = Integer.parseInt(taskToDelete) - 1;
            if (taskIndex >= 0 && taskIndex < taskCount) {
                for (int i = taskIndex; i < taskCount - 1; i++) {
                    tasks[i] = tasks[i + 1];
                }
                tasks[--taskCount] = null;
                JOptionPane.showMessageDialog(frame, "Task deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid task number.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input.");
        }
    }

    // Timer Section: Set Timer and Start Pomodoro Timer
    private static void setTimerAndStartPomodoro() {
        // Get user input for custom timer values
        String studyTime = JOptionPane.showInputDialog(frame, "Enter study time in minutes (default 25):", String.valueOf(studyMinutes));
        if (studyTime != null && !studyTime.isEmpty()) {
            studyMinutes = Integer.parseInt(studyTime);
        }

        String breakTime = JOptionPane.showInputDialog(frame, "Enter break time in minutes (default 5):", String.valueOf(breakMinutes));
        if (breakTime != null && !breakTime.isEmpty()) {
            breakMinutes = Integer.parseInt(breakTime);
        }

        // Start Pomodoro Timer
        startPomodoroTimer(studyMinutes);
    }

    private static void startPomodoroTimer(int studyMinutes) {
        remainingTime = studyMinutes * 60; // Convert minutes to seconds
        timerLabel.setText(formatTime(remainingTime));
        timerLabel.setVisible(true);
        blankLabel.setVisible(false); // Hide the blank label while the timer is running

        whiteLabel.setVisible(true); // Show white label after the timer starts

        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                timerLabel.setText(formatTime(remainingTime));

                if (remainingTime <= 0) {
                    countdownTimer.stop();
                    JOptionPane.showMessageDialog(frame, "Pomodoro session ended! Take a break.");
                    blankLabel.setVisible(true); // Show the blank label after the timer ends
                    whiteLabel.setVisible(false); // Hide white label after the timer ends
                    startPomodoroTimer(breakMinutes); // Start break timer
                }
            }
        });
        countdownTimer.start();
    }

    private static String formatTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private static void showMotivationalQuote() {
        String[] quotes = {
            "The only way to do great work is to love what you do.",
            "Success is the sum of small efforts, repeated day in and day out.",
            "Believe you can and you're halfway there.",
            "The best way to predict the future is to create it."
        };
        Random random = new Random();
        String randomQuote = quotes[random.nextInt(quotes.length)];
        JOptionPane.showMessageDialog(frame, randomQuote, "Motivational Quote", JOptionPane.INFORMATION_MESSAGE);
    }
}
