package prototype;

import java.io.*;
import java.util.*;

/*
 * Persistence Layer
 * Handles loading and saving of the entire system
 * using simple CSV-style storage inside the saved_data folder.
 *
 * Folder Structure Created Automatically:
 *
 * saved_data/
 *    aircraft_service/
 *        aircraft.csv
 *        accounts.csv
 *    accounts/
 *        ACC1.csv
 *        ACC2.csv
 */

public class Persistence {

    private static final String ROOT = "saved_data";
    private static final String AIRCRAFT_DIR = ROOT + "/aircraft_service";
    private static final String ACCOUNT_DIR = ROOT + "/accounts";

    private static final String AIRCRAFT_FILE = AIRCRAFT_DIR + "/aircraft.csv";
    private static final String ACCOUNTS_FILE = AIRCRAFT_DIR + "/accounts.csv";

    /* ==============================
       PUBLIC ENTRY POINTS
       ============================== */

    public static void load(AircraftService aircraftService, AccountService accountService) {

        createFolders();

        loadAircraft(aircraftService);
        loadAccounts(accountService);
        loadBookings(aircraftService, accountService);
    }

    public static void save(AircraftService aircraftService, AccountService accountService) {

        createFolders();

        saveAircraft(aircraftService);
        saveAccounts(accountService);
        saveBookings(aircraftService);
    }

    /* ==============================
       FOLDER CREATION
       ============================== */

    private static void createFolders() {

        new File(ROOT).mkdir();
        new File(AIRCRAFT_DIR).mkdir();
        new File(ACCOUNT_DIR).mkdir();
    }

    /* AIRCRAFT SAVE / LOAD */

    private static void saveAircraft(AircraftService aircraftService) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(AIRCRAFT_FILE))) {

            List<Aircraft> list = aircraftService.getAircraftList();

            for (Aircraft a : list) {

                writer.println(
                        a.getAircraftId() + "," +
                        a.getRoute() + "," +
                        a.getArrivalTime() + "," +
                        a.getDepartureTime() + "," +
                        a.getRows() + "," +
                        a.getCols()
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving aircraft.");
        }
    }

    private static void loadAircraft(AircraftService aircraftService) {

        File file = new File(AIRCRAFT_FILE);

        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                String id = parts[0];
                String route = parts[1];
                int atime = Integer.parseInt(parts[2]);
                int dtime = Integer.parseInt(parts[3]);
                int rows = Integer.parseInt(parts[4]);
                int cols = Integer.parseInt(parts[5]);

                aircraftService.addAircraft(id, route, rows, cols, atime, dtime);
            }

        } catch (Exception e) {
            System.out.println("Error loading aircraft.");
        }
    }

    /* ==============================
       ACCOUNT SAVE / LOAD
       ============================== */

    private static void saveAccounts(AccountService accountService) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE))) {

            List<Account> list = accountService.getAccountList();

            for (Account acc : list) {

                writer.println(
                        acc.getAccountId() + "," +
                        acc.hasBooking() + "," +
                        acc.getPassengerCount()
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving accounts.");
        }
    }

    private static void loadAccounts(AccountService accountService) {

        File file = new File(ACCOUNTS_FILE);

        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                String id = parts[0];

                accountService.addAccount(id);
            }

        } catch (Exception e) {
            System.out.println("Error loading accounts.");
        }
    }

    /* ==============================
       BOOKING SAVE
       ============================== */

    private static void saveBookings(AircraftService aircraftService) {

        List<Aircraft> aircraftList = aircraftService.getAircraftList();

        for (Aircraft aircraft : aircraftList) {

            List<Account> accounts = aircraft.getAccounts();

            for (Account acc : accounts) {

                Passenger[] passengers = acc.getPassengers();

                File file = new File(ACCOUNT_DIR + "/" + acc.getAccountId() + ".csv");

                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {

                    for (Passenger p : passengers) {

                        writer.println(
                                p.getName() + "," +
                                p.getAge() + "," +
                                aircraft.getAircraftId() + "," +
                                p.getSeat()
                        );
                    }

                } catch (IOException e) {
                    System.out.println("Error saving booking for account " + acc.getAccountId());
                }
            }
        }
    }

    /* ==============================
       BOOKING LOAD
       ============================== */

    private static void loadBookings(AircraftService aircraftService, AccountService accountService) {

        File folder = new File(ACCOUNT_DIR);

        File[] files = folder.listFiles();

        if (files == null) return;

        for (File f : files) {

            try (BufferedReader reader = new BufferedReader(new FileReader(f))) {

                String accountId = f.getName().replace(".csv", "");

                Account acc = accountService.getAccount(accountId);

                if (acc == null) continue;

                List<Passenger> passengers = new ArrayList<>();

                String line;

                String aircraftId = null;

                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split(",");

                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    aircraftId = parts[2];
                    String seat = parts[3];

                    passengers.add(new Passenger(name, age, seat));
                }

                if (aircraftId == null) continue;

                Aircraft aircraft = aircraftService.getAircraft(aircraftId);

                if (aircraft == null) continue;

                acc.setPassengers(passengers.toArray(new Passenger[0]));

                aircraft.addAccount(acc);

            } catch (Exception e) {
                System.out.println("Error loading booking file " + f.getName());
            }
        }
    }
}