public abstract class Employee                                                  // Employee compensation by Vanina
 {final String name, lastName, accountNumber;
  static int passed = 0;                                                        // Number of tests passed

  Employee(String Name, String LastName, String AccountNumber)
   {name = Name; lastName = LastName; accountNumber = AccountNumber;
   }

  abstract String sendPayCheck();                                               // Imprimir recibo
  abstract double calculateSalary();                                            // Salary for employee depends on employee type

  String depositarSueldo()                                                      // Depositarlo
   {return "Se depositó a la cuenta de: " + accountNumber;
   }

  String calculateTotalSalary()                                                 // Total salary for employee
   {final double sueldo = calculateSalary();
    if (sueldo < 0) throw new ClassCastException("La liquidación no pudo ser calculada");

    final String paycheck = sendPayCheck();                                     // Emitimos recibo porque el sueldo es mayor a 0
    String payment = depositarSueldo();                                         // Deposito solo porque lo pide el enunciado
    return "La liquidación generada es: " + paycheck + " Saldo a liquidar: " + sueldo; // Pisamos la respuesta con un nuevo String
   }

  final static class Effective extends Employee                                 // Effective employee
   {final double basicSalary, discounts, bonus;

    Effective(String Name, String LastName, String AccountNumber,
      double BasicSalary, double Discounts, double Bonus)
     {super(Name, LastName, AccountNumber);
      basicSalary = BasicSalary;
      discounts   = Discounts;
      bonus       = Bonus;
     }

    double calculateSalary() {return basicSalary + bonus - discounts;}
    String sendPayCheck   () {return "un documento impreso";}
   }

  final static class Contractor extends Employee                                // Contract employee
   {final double hoursWorked, rate;

    Contractor(String Name, String lastName, String AccountNumber,
      double HoursWorked, double Rate)
     {super(Name, lastName, AccountNumber);
      hoursWorked = HoursWorked;
      rate        = Rate;
     }

    double calculateSalary() {return hoursWorked * rate;}
    String sendPayCheck   () {return "un documento impreso";}
   }

// Tests

  final static Employee.Effective  effectiveEmployee           = new Employee.Effective ("Juan",     "Perez",   "aaa111",  400d,   40d, 60d);
  final static Employee.Contractor contractorEmployee          = new Employee.Contractor("Juan",     "Perez",   "aaa111",  201d,   40d);
  final static Employee.Effective  effectiveEmployeeException  = new Employee.Effective ("Juan",     "Perez",   "123456", 1000d, 2000d, 50d);
  final static Employee.Contractor contractorEmployeeException = new Employee.Contractor("Pompilia", "Pompini", "654321", -10d,   100d);

  static void test_CalculateEffectiveOk()
   {String e = "La liquidación generada es: un documento impreso Saldo a liquidar: 420.0";
    String r = effectiveEmployee.calculateTotalSalary();
    assert e.equals(r);
    passed++;
   }

  static void test_CalculateContractor()
   {String e = "La liquidación generada es: un documento impreso Saldo a liquidar: 8040.0";
    String r = contractorEmployee.calculateTotalSalary();
    assert e.equals(r);
    passed++;
   }

  static void test_CalculateContractorErrorPrint()
   {String e = "java.lang.ClassCastException: La liquidación no pudo ser calculada";
    try
     {String r = contractorEmployeeException.calculateTotalSalary();
     }
    catch(ClassCastException x)
     {assert(e.toString().equals(x.toString()));
      passed++;
     }
   }

  public static void main(String[] args)                                        // Run the tests
   {test_CalculateEffectiveOk();
    test_CalculateContractor();
    test_CalculateContractorErrorPrint();
    say("Passed", passed, "tests");
   }

  static void say(Object...O)
   {final StringBuilder b = new StringBuilder();
    for(Object o: O) {b.append(" "); b.append(o);}
    System.err.println((O.length > 0 ? b.substring(1) : ""));
   }
 }
