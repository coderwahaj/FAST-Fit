# 🏋️ FAST Fit — Gym Workout Planner App

**FAST Fit** is an Android application developed as part of Assignment-1.
The app helps users choose workouts, book gym slots, plan nutrition, and share their workout plan.

---

## 📚 Learning Outcomes

This project demonstrates practical use of core Android concepts:

* XML Layouts & Views
* Styling and Animations (anim folder)
* Multiple Activities
* Click Listeners
* Explicit & Implicit Intents
* Basic Input Validation
* Passing Data Between Activities (Intent Extras)
* Sending SMS via `SmsManager`
* Using `startActivity()` and `startActivityForResult()`

---

## 📱 App Navigation Flow

```
Splash Screen
     ↓
Onboarding Screen
     ↓
Workout List Screen
     ↓
Slot Booking Screen
     ↓
Nutrition Screen
     ↓
Workout Summary Screen
```

Each screen is implemented as a **separate Activity** and passes data forward using **Intents**.

---

## 🖥️ Screens Overview

### 1️⃣ Splash Screen (Launcher Activity)

* App logo animation (fade/rotate)
* Automatically moves to Onboarding screen after **5 seconds**
* Navigation via **Explicit Intent**

---

### 2️⃣ Onboarding Screen

* Displays welcome message & instructions
* Button → **Get Started**
* User must press button to proceed

---

### 3️⃣ Workout List Screen

Displays 3 machine workouts:

| Workout      | Features        |
| ------------ | --------------- |
| Leg Press    | Image + details |
| Lat Pulldown | Image + details |
| Chest Press  | Image + details |

Each workout has 2 buttons:

* **BOOK SLOT** → Slot Booking Screen (Explicit Intent)
* **WATCH TUTORIAL** → Opens YouTube (Implicit Intent)

**Data Passed Forward**

* Selected workout name

---

### 4️⃣ Slot Booking Screen

User selects available time slots:

* Morning
* Afternoon
* Evening

Features:

* Toggle slot selection
* Displays selected slot count

Buttons:

* **Proceed to Nutrition**
* **Confirm Workout**

**Validations**

* At least one slot must be selected before proceeding
* Slots become locked after moving forward

**Data Passed**

* Workout name
* Selected slot(s)

---

### 5️⃣ Nutrition Screen

User chooses optional nutrition items:

| Item          | Functionality     |
| ------------- | ----------------- |
| Protein Shake | Quantity selector |
| Energy Bar    | Quantity selector |
| Salad         | Quantity selector |
| Juice         | Quantity selector |

Features:

* Quantity cannot go below 0
* Total price updates dynamically

**Data Passed**

* Workout name
* Selected slot(s)
* Nutrition total cost

---

### 6️⃣ Workout Summary Screen

Displays complete plan:

* Workout name
* Selected slots
* Nutrition total

Buttons:

* **Send Plan** → Share via WhatsApp/Gmail (Implicit Intent)
* **Send SMS** → Sends confirmation SMS using `SmsManager`

**Validation**

* SMS only sent if all data is present

---

## 🛠️ Tech Stack

* Language: **Java / Kotlin**
* IDE: **Android Studio**
* Min SDK: (Add your value)
* Target SDK: (Add your value)

---

## ▶️ How to Run the Project

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/Fast-Fit.git
   ```

2. Open project in **Android Studio**

3. Sync Gradle and run on:

   * Emulator OR
   * Physical Android device

---

## 🎯 Key Concepts Implemented

* Multi-Activity navigation
* Intent data passing
* UI validations
* Implicit & Explicit intents
* SMS integration
* Animations
* Dynamic UI updates

---

## 👨‍💻 Author

**Wahaj Asif**
