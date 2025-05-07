# StockWise

StockWise is a financial asset management application designed for brokerage firms (CTVMs) to manage investment portfolios acquired by their clients. The platform enables the registration, consultation, and control of financial assets such as stocks, real estate funds (REITs), government bonds, CDBs, RDBs, LCs, LCIs, CRAs, CRIs, debentures, and crypto assets.

## 🚀 Technologies Used

### Back-end
- **Language:** Java
- **Framework:** Spring Boot
- **Architecture:** RESTful API
- **Design Patterns (GoF):** Singleton, Factory Method, Repository
- **Database:** MySQL

### Front-end
- **Languages:** HTML, CSS, React

## ⚙️ Features

- Client registration with CPF, full name, birth date, agency, account, phone number, and address
- Investor profile classification: conservative, moderate, bold, sophisticated, and aggressive
- Asset registration with code, name, and description
- Linking issuers, start and end dates to specific assets
- Issuance and management of brokerage notes, including:
  - Note number and page
  - Purchase date
  - Purchased asset code
  - Quantity acquired
  - Asset price and total value
  - Involved costs (fees, taxes, etc.)

## ▶️ How to Run the Project

### Back-end

1. Clone the repository:

    git clone https://github.com/seu-usuario/stockwise.git

2. Navigate to the project directory:

    cd stockwise

3. Configure your database connection in `application.properties`

4. Build and run the project using Maven:

    mvn spring-boot:run

### Front-end

1. Navigate to the `frontend` directory:

    cd frontend

2. Open the `index.html` file in your browser

## 🤝 Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request.

## 📝 License

This project is licensed under the MIT License. See the `LICENSE` file for more information.

---
**Developed by [João Pedro Carmo & Team]**
