import java.util.*;

class Movie {
    private String title;
    private int totalSeats;
    private int availableSeats;
    private double ticketPrice;

    public Movie(String title, int totalSeats, double ticketPrice) {
        this.title = title;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.ticketPrice = ticketPrice;
    }

    public String getTitle() {
        return title;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public boolean bookSeats(int count) {
        if (count <= availableSeats) {
            availableSeats -= count;
            return true;
        } else {
            return false;
        }
    }

    public void cancelSeats(int count) {
        availableSeats += count;
    }

    public void showMovieDetails() {
        System.out.println("Title: " + title);
        System.out.println("Available Seats: " + availableSeats);
        System.out.println("Ticket Price: " + ticketPrice);
    }
}

class Booking {
    private static int bookingCounter = 1000;
    private int bookingId;
    private String customerName;
    private Movie movie;
    private int numberOfSeats;
    private double totalPrice;

    public Booking(String customerName, Movie movie, int numberOfSeats) {
        this.bookingId = bookingCounter++;
        this.customerName = customerName;
        this.movie = movie;
        this.numberOfSeats = numberOfSeats;
        this.totalPrice = numberOfSeats * movie.getTicketPrice();
    }

    public int getBookingId() {
        return bookingId;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void showBookingDetails() {
        System.out.println("----- Booking Details -----");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Customer: " + customerName);
        System.out.println("Movie: " + movie.getTitle());
        System.out.println("Seats: " + numberOfSeats);
        System.out.println("Total Price: " + totalPrice);
    }
}

public class MovieTicketBookingSystem {
    private static HashMap<Integer, Movie> movieMap = new HashMap<>();
    private static ArrayList<Booking> bookingHistory = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initMovies();
        int choice;
        do {
            System.out.println("\n===== Movie Ticket Booking System =====");
            System.out.println("1. View Movies");
            System.out.println("2. Book Tickets");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Booking History");
            System.out.println("5. Sort Movies");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showMovies();
                case 2 -> bookTicket();
                case 3 -> cancelBooking();
                case 4 -> showBookingHistory();
                case 5 -> sortMoviesMenu();
                case 6 -> System.out.println("Thank you for using the system. Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 6);
    }

    private static void initMovies() {
        movieMap.put(1, new Movie("Inception", 20, 250.0));
        movieMap.put(2, new Movie("Interstellar", 40, 300.0));
        movieMap.put(3, new Movie("Dune", 60, 280.0));
        movieMap.put(4, new Movie("The Batman", 45, 260.0));
        movieMap.put(5, new Movie("The Dark Knight", 25, 310.0));
        movieMap.put(6, new Movie("Tenet", 20, 180.0));
        movieMap.put(7, new Movie("Se7en", 55, 340.0));
        movieMap.put(8, new Movie("Shutter Island", 8, 210.0));
        movieMap.put(9, new Movie("Oppenheimer", 15, 310.0));
    }

    private static void showMovies() {
        System.out.println("\n----- Available Movies -----");
        for (Map.Entry<Integer, Movie> entry : movieMap.entrySet()) {
            Movie m = entry.getValue();
            System.out.println(entry.getKey() + ". " + m.getTitle() +
                               " | " + m.getTicketPrice() +
                               " | Available Seats: " + m.getAvailableSeats());
        }
    }

    private static void bookTicket() {
        showMovies();
        System.out.print("Enter movie number to book: ");
        int movieId = sc.nextInt();
        sc.nextLine();

        if (!movieMap.containsKey(movieId)) {
            System.out.println("Invalid movie selection.");
            return;
        }

        Movie selectedMovie = movieMap.get(movieId);
        selectedMovie.showMovieDetails();

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter number of seats to book: ");
        int seats = sc.nextInt();

        if (seats <= 0) {
            System.out.println("Seat count must be greater than 0.");
            return;
        }

        if (selectedMovie.bookSeats(seats)) {
            Booking booking = new Booking(name, selectedMovie, seats);
            bookingHistory.add(booking);
            System.out.println("Booking successful!");
            booking.showBookingDetails();
        } else {
            System.out.println("Not enough seats available.");
        }
    }

    private static void cancelBooking() {
        if (bookingHistory.isEmpty()) {
            System.out.println("No bookings to cancel.");
            return;
        }

        System.out.print("Enter Booking ID to cancel: ");
        int cancelId = sc.nextInt();

        Booking bookingToCancel = null;
        for (Booking b : bookingHistory) {
            if (b.getBookingId() == cancelId) {
                bookingToCancel = b;
                break;
            }
        }

        if (bookingToCancel != null) {
            bookingToCancel.getMovie().cancelSeats(bookingToCancel.getNumberOfSeats());
            bookingHistory.remove(bookingToCancel);
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("Booking ID not found.");
        }
    }

    private static void showBookingHistory() {
        if (bookingHistory.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\n----- Booking History -----");
        for (Booking booking : bookingHistory) {
            booking.showBookingDetails();
            System.out.println("--------------------------");
        }
    }

    private static void sortMoviesMenu() {
        ArrayList<Movie> movieList = new ArrayList<>(movieMap.values());

        System.out.println("1. Sort by Ticket Price (Low to High)");
        System.out.println("2. Sort by Available Seats (High to Low)");
        System.out.print("Choose sorting option: ");
        int option = sc.nextInt();

        switch (option) {
            case 1 -> movieList.sort(Comparator.comparingDouble(Movie::getTicketPrice));
            case 2 -> movieList.sort((a, b) -> b.getAvailableSeats() - a.getAvailableSeats());
            default -> {
                System.out.println("Invalid option.");
                return;
            }
        }

        System.out.println("\n----- Sorted Movies -----");
        for (Movie m : movieList) {
            System.out.println(m.getTitle() + " | " + m.getTicketPrice() +
                               " | Available: " + m.getAvailableSeats());
        }
    }
}
