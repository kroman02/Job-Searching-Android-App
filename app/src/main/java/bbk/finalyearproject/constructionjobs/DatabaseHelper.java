package bbk.finalyearproject.constructionjobs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper implements UserDataHandler {

    // Database info
    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "User";
    private static final String TABLE_LISTING = "Listings";
    private static final String TABLE_RATES = "Rates";


    // User table
    private static final String COLUMN_UID = "uid";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_PROFESSION = "profession";
    private static final String COLUMN_USER_CONTACT = "contact";
    private static final String COLUMN_USER_LOCATION = "user_location";

    // Listing table
    private static final String COLUMN_LID = "lid";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_BODY = "body";
    private static final String COLUMN_CONTACT= "contact";
    private static final String COLUMN_LISTING_LOCATION = "listing_location";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_DATE_CREATED = "date";

    // Rating table
    private static final String COLUMN_RATER_ID = "rater";
    private static final String COLUMN_RATED_ID = "rated";
    private static final String COLUMN_RATE = "rate";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create User table query
        String createUserTable = "CREATE TABLE " + TABLE_USER + "( "
                                  + COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                  + COLUMN_USERNAME + " TEXT, "
                                  + COLUMN_PASSWORD + " TEXT, "
                                  + COLUMN_NAME + " TEXT, "
                                  + COLUMN_SURNAME + " TEXT, "
                                  + COLUMN_PROFESSION + " TEXT, "
                                  + COLUMN_USER_CONTACT + " TEXT, "
                                  + COLUMN_USER_LOCATION + " TEXT" + ")";

        // Create Listing table query
        String createListingTable = "CREATE TABLE " + TABLE_LISTING + "( "
                                  + COLUMN_LID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                  + COLUMN_TITLE + " TEXT, "
                                  + COLUMN_BODY + " TEXT, "
                                  + COLUMN_CONTACT + " TEXT, "
                                  + COLUMN_LISTING_LOCATION + " TEXT, "
                                  + COLUMN_AUTHOR + " TEXT, "
                                  + COLUMN_DATE_CREATED + " TEXT" + ")";

        // Create Rate table
        String createRateTable = "CREATE TABLE " + TABLE_RATES + "( "
                + COLUMN_RATER_ID + " INTEGER NOT NULL, "
                + COLUMN_RATED_ID + " INTEGER NOT NULL, "
                + COLUMN_RATE + " REAL NOT NULL, "
                + "PRIMARY KEY ("+ COLUMN_RATER_ID+", "+COLUMN_RATED_ID+"))";

        // Executing SQL statements
        db.execSQL(createUserTable);
        db.execSQL(createListingTable);
        db.execSQL(createRateTable);
    }

    // METHODS TO WORK WITH USER TABLE

    /**
     * Method to add single user to the database
     * @param user
     * @return true or false
     */
    public boolean addUser(User user) {

        // Object that allows writing to the database
        SQLiteDatabase dbWriter = this.getWritableDatabase();
        // Object that collects and associates information with columns
        ContentValues content = new ContentValues();

        content.put(COLUMN_USERNAME, user.getUsername());
        content.put(COLUMN_PASSWORD, user.getPassword());
        content.put(COLUMN_NAME, user.getName());
        content.put(COLUMN_SURNAME, user.getSurname());
        content.put(COLUMN_PROFESSION, user.getProfession());
        content.put(COLUMN_USER_CONTACT, user.getContact());
        content.put(COLUMN_USER_LOCATION, user.getLocation());

        long insert = dbWriter.insert(TABLE_USER, null, content);
        dbWriter.close();
        return insert > -1;
    }

    /**
     * Method to retrieve all users from the database
     * @return arraylist of users
     */
    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<User>();
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase dbReader = this.getReadableDatabase();
        Cursor cursor = dbReader.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                int uid = cursor.getInt(0);
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                String name = cursor.getString(3);
                String surname = cursor.getString(4);
                String profession = cursor.getString(5);
                String contact = cursor.getString(6);
                String location = cursor.getString(7);
                userList.add(new User(uid, username, password, name, surname, profession, contact, location));
            } while(cursor.moveToNext());

        }
        cursor.close();
        dbReader.close();
        return userList;
    }

    /**
     * Method to retrieve one user
     * @return a user
     */
    public User getUser(int uid) {
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase dbReader = this.getReadableDatabase();
        Cursor cursor = dbReader.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(0) == uid) {
                   int id = uid;
                   String username = cursor.getString(1);
                   String password = cursor.getString(2);
                   String name = cursor.getString(3);
                   String lastName = cursor.getString(4);
                   String profession = cursor.getString(5);
                   String contact = cursor.getString(6);
                   String location = cursor.getString(7);
                   User user = new User(id, username, password, name, lastName, profession, contact, location);
                   dbReader.close();
                   return user;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbReader.close();
        return null;
    }


    /**
     * Method to retrieve one user by username
     * @return a user
     */
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase dbReader = this.getReadableDatabase();
        Cursor cursor = dbReader.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(1).equalsIgnoreCase(username)) {
                    int id = cursor.getInt(0);
                    String password = cursor.getString(2);
                    String name = cursor.getString(3);
                    String lastName = cursor.getString(4);
                    String profession = cursor.getString(5);
                    String contact = cursor.getString(6);
                    String location = cursor.getString(7);
                    User user = new User(id, username, password, name, lastName, profession, contact, location);
                    dbReader.close();
                    return user;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbReader.close();
        return null;
    }


    /**
     * Method to add single listing to the database
     * @param listing
     * @return true or false
     */
    public boolean addListing(Listing listing) {

        // Object that allows writing to the database
        SQLiteDatabase dbWriter = this.getWritableDatabase();
        // Object that collects and associates information with columns
        ContentValues content = new ContentValues();

        content.put(COLUMN_TITLE, listing.getTitle());
        content.put(COLUMN_BODY, listing.getBody());
        content.put(COLUMN_CONTACT, listing.getContact());
        content.put(COLUMN_LISTING_LOCATION, listing.getLocation());
        content.put(COLUMN_AUTHOR, listing.getAuthor());
        content.put(COLUMN_DATE_CREATED, listing.getDate());

        long insert = dbWriter.insert(TABLE_LISTING, null, content);
        dbWriter.close();
        return insert > -1;
    }

    /**
     * Method to retrieve all listings from the database
     * @return arraylist of listings
     */
    public List<Listing> getAllListings() {

        List<Listing> listingList = new ArrayList<Listing>();
        String query = "SELECT * FROM " + TABLE_LISTING;
        SQLiteDatabase dbReader = this.getReadableDatabase();
        Cursor cursor = dbReader.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                int lid = cursor.getInt(0);
                String title = cursor.getString(1);
                String body = cursor.getString(2);
                String contact = cursor.getString(3);
                String location = cursor.getString(4);
                String author = cursor.getString(5);
                String date = cursor.getString(6);
                listingList.add(new Listing(lid, title, body, contact, location, author, date));
            } while(cursor.moveToNext());

        }
        cursor.close();
        dbReader.close();
        return listingList;
    }

    /**
     * Method to retrieve one user
     * @return a user
     */
    public Listing getListing(int lid) {
        String query = "SELECT * FROM " + TABLE_LISTING;
        SQLiteDatabase dbReader = this.getReadableDatabase();
        Cursor cursor = dbReader.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(0) == lid) {
                    String title = cursor.getString(1);
                    String body = cursor.getString(2);
                    String contact = cursor.getString(3);
                    String location = cursor.getString(4);
                    String author = cursor.getString(5);
                    String date = cursor.getString(6);
                    Listing listing = new Listing(lid, title, body, contact, location, author, date);
                    dbReader.close();
                    return listing;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbReader.close();
        return null;
    }



    // LOGIN AND REGISTER METHODS

    public boolean usernameExists(String username) {
        SQLiteDatabase dbReader = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_USER;
        Cursor cursor = dbReader.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(0).equalsIgnoreCase(username)) {
                    dbReader.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        dbReader.close();
        cursor.close();
        return false;
    }

    public User logIn(String username, String password) {
        User user = null;
        SQLiteDatabase dbReader = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_UID + ", " + COLUMN_USERNAME + ", " + COLUMN_PASSWORD + " FROM " + TABLE_USER;

        Cursor cursor = dbReader.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(1).equalsIgnoreCase(username) && cursor.getString(2).equals(password)) {
                    user = this.getUser(cursor.getInt(0));
                }
            } while (cursor.moveToNext());

        }
        cursor.close();
        dbReader.close();
        return user;
    }

    public boolean addRating(Rating rating) {

        // Object that allows writing to the database
        SQLiteDatabase dbWriter = this.getWritableDatabase();
        // Object that collects and associates information with columns
        ContentValues content = new ContentValues();

        content.put(COLUMN_RATER_ID, rating.getRater());
        content.put(COLUMN_RATED_ID,  rating.getRated());
        content.put(COLUMN_RATE,  rating.getRate());


        long insert = dbWriter.insert(TABLE_RATES, null, content);
        dbWriter.close();
        return insert > -1;
    }

    public boolean ratingExists(int rater, int rated) {
        String primaryKeyPassed = rater + "" + rated;
        String query = "SELECT * FROM " + TABLE_RATES;
        SQLiteDatabase dbReader = this.getReadableDatabase();
        Cursor cursor = dbReader.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int raterDB = cursor.getInt(0);
                int ratedDB = cursor.getInt(1);
                String primaryKeyStored = raterDB + "" + ratedDB;
                if (primaryKeyPassed.equalsIgnoreCase(primaryKeyStored)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    public float getAverageRate(int id){
        String idString = ""+id;
        String query = "SELECT ROUND(AVG("+COLUMN_RATE+"), 2) FROM " + TABLE_RATES+" WHERE "+COLUMN_RATED_ID+" = "+idString.trim();
        SQLiteDatabase dbReader = this.getReadableDatabase();
        Cursor cursor = dbReader.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getFloat(0);
        }
        return (float) 0.00;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
