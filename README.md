# NFT_BASIC_COMPREHENCIVE
# Assignment Overview

This document covers programming fundamentals, object-oriented programming (OOP), testing strategies, performance testing, version control, and CI/CD setup. It includes code examples, test plans, and pipeline configurations, with supporting files listed at the end.

---

## Question 1: Programming Fundamentals & OOP

### 1.1 Procedural Code

The following Java code reads an array of student names and scores, calculates the average score, and prints the names of students with scores above the average. It uses basic loops and conditions but lacks modularity.

**Figure 1: Procedural Java Code for Student Scores**

```java
import java.util.Scanner;

public class StudentScores {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Read number of students
        System.out.print("Enter number of students: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String[] names = new String[n];
        double[] scores = new double[n];
        
        // Read names and scores
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name for student " + (i + 1) + ": ");
            names[i] = scanner.nextLine();
            System.out.print("Enter score for student " + (i + 1) + ": ");
            scores[i] = scanner.nextDouble();
            scanner.nextLine();
        }
        
        // Calculate average
        double sum = 0;
        for (double score : scores) {
            sum += score;
        }
        double average = sum / n;
        
        // Print students above average
        System.out.println("Average score: " + average);
        System.out.println("Students with scores above average:");
        for (int i = 0; i < n; i++) {
            if (scores[i] > average) {
                System.out.println(names[i] + ": " + scores[i]);
            }
        }
        
        scanner.close();
    }
}
```

The full code is provided in the accompanying file `StudentScores.java`.

### 1.2 OOP Refactored Code

The refactored solution uses OOP principles. The `Student` class encapsulates a student’s name and score, while the `StudentAnalyzer` class handles logic for calculating the average and displaying above-average students.

**Figure 2: OOP Java Code for Student Scores**

```java
class Student {
    private String name;
    private double score;
    
    public Student(String name, double score) {
        this.name = name;
        this.score = score;
    }
    
    public String getName() {
        return name;
    }
    
    public double getScore() {
        return score;
    }
}

class StudentAnalyzer {
    private Student[] students;
    
    public StudentAnalyzer(Student[] students) {
        this.students = students;
    }
    
    public double calculateAverageScore() {
        double sum = 0;
        for (Student student : students) {
            sum += student.getScore();
        }
        return students.length > 0 ? sum / students.length : 0;
    }
    
    public void displayStudentsAboveAverage() {
        double average = calculateAverageScore();
        System.out.println("Average score: " + average);
        System.out.println("Students with scores above average:");
        for (Student student : students) {
            if (student.getScore() > average) {
                System.out.println(student.getName() + ": " + student.getScore());
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of students: ");
        int n = scanner.nextInt();
        scanner.nextLine();
        
        Student[] students = new Student[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name for student " + (i + 1) + ": ");
            String name = scanner.nextLine();
            System.out.print("Enter score for student " + (i + 1) + ": ");
            double score = scanner.nextDouble();
            scanner.nextLine();
            students[i] = new Student(name, score);
        }
        
        StudentAnalyzer analyzer = new StudentAnalyzer(students);
        analyzer.displayStudentsAboveAverage();
        
        scanner.close();
    }
}
```

The full code is provided in the accompanying file `Main.java`.

### 1.3 OOP Benefits

Using OOP principles improves code organization:

- **Encapsulation**: The `Student` class hides internal data (name, score) and exposes only necessary methods, reducing direct access risks.
- **Modularity**: The `StudentAnalyzer` class separates logic, making it easier to maintain or extend (e.g., adding new analysis methods).
- **Reusability**: Classes can be reused in other applications.
- **Maintainability**: Changes are localized, reducing the risk of introducing errors.

---

## Question 2: Test Strategy, Planning, and Types of Testing

### 2.1 Integration Testing

- **Purpose**: Ensures multiple modules or services interact correctly by testing their interfaces and data flow.
- **Example**: For Demoblaze, test the integration between the product catalog and cart services. Verify that adding a product via `/cart/add` updates the cart’s database and returns the correct total.
- **Approach**: Use mock APIs for external services (e.g., payment gateway) and validate data consistency across endpoints.

### 2.2 Performance Testing

- **Purpose**: Evaluates system performance under load, focusing on speed, scalability, and stability.
- **Key Metrics**:
  - **Response Time**: Time to process a request.
  - **Throughput**: Requests per second.
  - **Error Rate**: Percentage of failed requests.
  - **CPU/Memory Utilization**: Server resource usage.
- **Example Scenario**: Load test Demoblaze’s checkout page with 500 concurrent users during a sale to ensure stability.

### 2.3 Security Testing

- **Purpose**: Identifies vulnerabilities to protect sensitive data.
- **Common Vulnerabilities**:
  - **SQL Injection**: Test inputs to prevent malicious queries.
  - **Cross-Site Scripting (XSS)**: Ensure inputs are sanitized to prevent script injection.
  - **Broken Authentication**: Verify session management and token expiration.
- **Mitigation Strategy**:
  - Use parameterized queries for SQL injection.
  - Implement Content Security Policy (CSP) and input sanitization for XSS.
  - Use secure JWT tokens with short expiration times and HTTPS.

**Table: Overview of Testing Types**

| Testing Type         | Purpose                          | Example Scenario                        |
|----------------------|----------------------------------|-----------------------------------------|
| Integration Testing  | Ensure module interactions       | Product catalog and cart integration   |
| Performance Testing  | Evaluate system under load       | 500-user checkout during sale          |
| Security Testing     | Identify vulnerabilities         | Prevent SQL injection in login form    |

---

## Question 3: Performance Testing & Optimization

### 3.1 Performance Testing Scenarios

The following scenarios test https://www.demoblaze.com/:

1. **1000 Users Browsing Product Page**:
   - Simulate GET requests to `/prod.html?idp_=1` for various product IDs.
2. **500 Concurrent Checkouts**:
   - Test POST requests to `/checkout` with payment methods (e.g., credit card, PayPal).
3. **Cart Updates with Multiple Items**:
   - Simulate POST requests to `/cart/add` and `/cart/remove` with multiple items.
4. **API Response Times for Product Search**:
   - Test GET requests to `/search?query=phone` with varying queries.

### 3.2 JMeter Test Plan

The JMeter test plan simulates the above scenarios. The full plan is provided in `demoblaze_load_test.jmx`. Below is a summary:

**Figure 3: JMeter Test Plan Summary**

```xml
<!-- Thread Group: 1000 Users Browsing -->
<ThreadGroup testname="1000 Users Browsing">
  <stringProp name="ThreadGroup.num_threads">1000</stringProp>
  <stringProp name="ThreadGroup.ramp_up">10</stringProp>
  <stringProp name="ThreadGroup.duration">60</stringProp>
</ThreadGroup>
<HTTPSamplerProxy testname="GET Product Page">
  <stringProp name="HTTPSampler.domain">www.demoblaze.com</stringProp>
  <stringProp name="HTTPSampler.path">/prod.html?idp_=${productId}</stringProp>
  <stringProp name="HTTPSampler.method">GET</stringProp>
</HTTPSamplerProxy>
<!-- Additional Thread Groups for Checkouts, Cart Updates, Search -->
```

**Execution Steps**:

1. Install Apache JMeter.
2. Open `demoblaze_load_test.jmx`.
3. Configure variables (e.g., `productId`, `userId`) using CSV Data Set Config.
4. Run: `jmeter -n -t demoblaze_load_test.jmx -l results.jtl`.
5. Analyze results in the Summary Report.

See `demoblaze_load_test.jmx` for the complete test plan.

### 3.3 Performance Metrics

- **Response Time**: Target <2 seconds for 99% of requests.
- **Throughput**: Ensure 100+ requests per second.
- **Error Rate**: Aim for <1% failed requests.
- **CPU/Memory Utilization**: Keep below 80% usage.

**Table 1: Performance Metrics Targets**

| Metric                 | Target                     |
|------------------------|----------------------------|
| Response Time          | <2 seconds (99% requests)  |
| Throughput             | 100+ requests/second       |
| Error Rate             | <1%                        |
| CPU/Memory Utilization | <80%                       |

---

## Question 4: Version Control & CI/CD Setup

To set up a Jenkins job for automatic builds and tests:

1. **Prerequisites**: Jenkins installed, Maven and JDK configured, GitHub repository with Java code.
2. **Steps**:
   - Create a Freestyle project named “StudentScoreAnalyzer”.
   - Configure Source Code Management: Set Git URL (e.g., `https://github.com/username/student-score-analyzer`).
   - Add GitHub webhook: Set Payload URL to `http://<jenkins-url>/github-webhook/` for push events.
   - Enable “GitHub hook trigger for GITScm polling”.
   - Add Build Step: “Invoke top-level Maven targets” with `clean test package`.
   - Add Post-Build Action: “Publish JUnit test result report” (`target/surefire-reports/*.xml`).
   - Save and test by pushing a change to GitHub.

**Commands**:

```bash
# Start Jenkins
java -jar jenkins.war

# Maven build
mvn clean test package
```

---

## Question 5: Jenkins & CI/CD Pipeline as Code

The Jenkins pipeline automates checkout, build, test, and deployment.

**Figure 4: Jenkinsfile for CI/CD Pipeline**

```groovy
pipeline {
    agent any
    tools {
        maven 'Maven3'
        jdk 'JDK11'
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/username/student-score-analyzer.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Deploy to Staging') {
            steps {
                sh 'scp target/student-score-analyzer.jar user@staging-server:/app'
                sh 'ssh user@staging-server "java -jar /app/student-score-analyzer.jar"'
            }
        }
    }
}
```

The full pipeline is provided in `Jenkinsfile`.

**Explanation**:

- **Checkout**: Pulls code from GitHub.
- **Build**: Compiles the Java code.
- **Test**: Runs unit tests and publishes results.
- **Package**: Creates a JAR file.
- **Deploy**: Copies and runs the JAR on a staging server.

---

## Supporting Files

The submission package includes:

- **`StudentScores.java`**: Procedural Java code.
- **`Main.java`**: OOP Java code.
- **`demoblaze_load_test.jmx`**: JMeter test plan.
- **`Jenkinsfile`**: Jenkins pipeline script.

---
