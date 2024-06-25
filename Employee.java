public abstract class Employee                                                  // Employee compensation by Vanina
 {String name, lastName, accountNumber;

  Employee() {}

  Employee(String Name, String LastName, String AccountNumber)
   {name = Name; lastName = LastName; accountNumber = AccountNumber;
   }

  abstract String sendPayCheck();                                               // Imprimir recibo

  String depositarSueldo()                                                      // Depositarlo
   {return "Se depositó a la cuenta de: " + accountNumber;
   }

  abstract double calculateSalary();

  String calculateTotalSalary()
   {String response = "La liquidación no pudo ser calculada";
    double sueldo = calculateSalary();
    if (sueldo < 0) throw new ClassCastException("La liquidación no pudo ser calculada");

    String paycheck = sendPayCheck();                                           // Emitimos recibo porque el sueldo es mayor a 0
    response = "La liquidación generada es: " + paycheck + " Saldo a liquidar: " + sueldo; // Pisamos la respuesta con un nuevo String
    String payment = depositarSueldo();                                         // Deposito solo porque lo pide el enunciado
    return response;
   }

  static class Effective extends Employee                                       // Effective employee
   {double basicSalary, discounts, bonus;

    Effective(String Name, String LastName, String AccountNumber,
      double BasicSalary, double Discounts, double Bonus)
     {super(Name, LastName, AccountNumber);
      basicSalary = BasicSalary;
      discounts   = Discounts;
      bonus       = Bonus;
     }

    Effective() {}

    double calculateSalary() {return basicSalary + bonus - discounts;}

    String sendPayCheck   () {return "un documento impreso";}
   }

  static class Contractor extends Employee                                      // Contract employee
   {double hoursWorked, rate;

    Contractor(String Name, String lastName, String AccountNumber,
      double HoursWorked, double Rate)
     {super(Name, lastName, AccountNumber);
      hoursWorked = HoursWorked;
      rate        = Rate;
     }

    Contractor() {}

    double calculateSalary() {return hoursWorked * rate;}
    String sendPayCheck   () {return "un documento impreso";}
   }

// Tests

  final static Employee.Effective  effectiveEmployee           = new Employee.Effective ("Juan",     "Perez",   "aaa111",  400d,   40d, 60d);
  final static Employee.Contractor contractorEmployee          = new Employee.Contractor("Juan",     "Perez",   "aaa111",  201d,   40d);
  final static Employee.Effective  effectiveEmployeeException  = new Employee.Effective ("Juan",     "Perez",   "123456", 1000d, 2000d, 50d);
  final static Employee.Contractor contractorEmployeeException = new Employee.Contractor("Pompilia", "Pompini", "654321", -10d,   100d);

  static void testCalculateEffectiveOk()
   {String e = "La liquidación generada es: un documento impreso Saldo a liquidar: 420.0";
    String r = effectiveEmployee.calculateTotalSalary();
    assert e.equals(r);
   }

  static void testCalculateContractorErrorPrint()
   {String e = "La liquidación generada es: un documento impreso Saldo a liquidar: 8040.0";
    String r = contractorEmployee.calculateTotalSalary();
    assert e.equals(r);
   }

  public static void main(String[] args)                                        // Test
   {System.out.println("Work Phil!");
    var e = new Employee.Effective("Juan", "Perez", "aaa123", 100d, 10d, 60d);
    System.out.println(e.name);
    testCalculateEffectiveOk();
    testCalculateContractorErrorPrint();
   }

  static void say(Object...O)
   {final StringBuilder b = new StringBuilder();
    for(Object o: O) {b.append(" "); b.append(o);}
    System.err.println((O.length > 0 ? b.substring(1) : ""));
   }
 }
