# Vaccine-Drive-Registration-System :syringe:

Vaccine Drive Registration System is a simple application developed to improve the vaccination process by making it an easy-to-use platform. The main idea is to mimic the **CoWIN** potral, which is the vaccine drive system widely used in India. The back-end of the application is built with `Java` and the front-end is developed using `Java Swing` with the help of **Netbeans IDE**. The application begins with a simple login procedure that requires the consumer to enter his/her phone number, followed by the OTP sent to the specified phone number. Successful, entry into the application allows the user to use various features like viewing the statistical data of the overall vaccination status, scheduling an appointment, adding a beneficiary, downloading certificate of vaccinated beneficiaries and so on. For a given account/phone number, a consumer will be allowed to add upto four beneficiaries and schedule an each for each one of them individually. The consumer can book a slot by applying filters of district, date and pin code. The various vaccination centers satisfying the filter constraints will be displayed along with the crowdness level predicted for the center, among which the user will be allowed to choose a vaccination center of his/her convenience and also the vaccine type of their preference. After getting vaccinated, the download certificate option will be provided to user through which the user will able to download a detailed certificate as PDF. On the vaccinator end, the professional will be allowed to login using OTP authentication and then specify the details of the beneficiary taking the inoculation shots. The vaccinator will also be allowed update the count of each type of vaccines on recent stock arrivals or other situations. Thus the application provides a seemless experience of all the basic functionalities required for the **Vaccine Drive Registartion System**.

## File Description :file_folder:

- **OTPLogin_1.java** : This frame is used by a beneficiary or vaccinator to login into the application by entering the mobile number. After clicking on "GET OTP" button, an OTP will be sent to the respective mobile number.
- **OTP.java** : This is used for sending a randomly generated OTP to the entered mobile number.
- **OTPLogin_2.java** : This is used for OTP authentication.
- **RegisterBeneficiary.java** : This class is used to add a new beneficary to the logged in mobile number. This frame gets the beneficiary details from the user and updates the database.
- **Register_beneficiary.java** : This frame provides the front end part for registering new beneficary to the mobile number.
- **AccountDetails.java** : This class is used to fetch the information of beneficaries registered to a given mobile number.
- **Registered_Beneficiary.java** : This frame displays the beneficiaries registered to the given mobile number.
- **Schedule_Appointment.java** : This frame provides the front end part for searching,filtering vaccination centers and booking a new vaccination appointment.
- **Predictcrowd.java** : This is used to connect the python to Java and run the ML model for predicting the crowdedness in the available vaccination centers.
- **Beneficiary_page.java** : This frame provides the front end part for ading member into a newly registered mobile number(without any registered beneficiaries)
- **ScheduleAppointment.java** : This frame provides the backend part for the retrieval of vaccination centers and booking appointments.
- **Confirm_Appointment.java** : This frame is used to confirm an appointment and insert record into the appointment details table.
- **Certificate_Generation.java** : This class is used to generate and download the certificate for the vaccinated beneficiary.
- **Statistics_Form.java** : This frame displays the overall vaccination statistics in the form of plots.
- **Stats.java** : This class is used to fetch the database to provide statistical data.
- **Vaccinator.java** : It provides the front-end frame for the vaccinator and enables the vaccinator to update the available vaccination shot.
- **Vaccination_Administrate.java** :  This class is used to carry out the operations to be executed when a beneficiary takes an inoculation shot at a vaccination center.
- **VaccineDriveRegistrationSystem.java** : This class is used to store the current login details(of beneficiary / vaccinator)
- **home_page.java** : This frame is displayed after a beneficiary logs into the system. 

## Software Tools Used :hammer_and_wrench:

### Front End
- `Java Swing`

### Back End
- `Java`
- `Python`
- `MySQL`

### IDEs
- **Netbeans** : For major part of `Java` and `Java Swing`
- **Google Colab** : For analysis of `machine learning` model
- **MySQL Workbench** : For maintenance of database

## Download and Usage
The code can be downloaded as a compressed zip file from the github web interface.

The repository can also be cloned using:
```
git clone https://github.com/BarathKumarBK-15/Vaccine-Drive-Registration-System.git
```

- Import the downloaded project using **Netbeans**
- Link the various `jar` files downloaded along with the project code
- Install the required python modules like `pandas`, `holidays`, etc
- Create a database named **VDRS** in `MySQL` and run the queries given alongside the code
- Build and run the project in the **Netbeans** IDE
