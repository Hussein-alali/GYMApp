import 'package:flutter/material.dart';
import 'Detalispage.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Calorie App',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
      ),
      home: const HomePage(),
    );
  }
}

// Data Class
class UserResult {
  final double calories;
  final int protein;
  final int carbs;
  final int fats;

  UserResult({
    required this.calories,
    required this.protein,
    required this.carbs,
    required this.fats
  });
}

// Global list for Dropdown
final List<double> _activityValues = [1.2, 1.375, 1.55, 1.725, 1.9];
final Map<double, String> _activityLabels = {
  1.2: 'Sedentary (Little to no exercise)',
  1.375: 'Lightly Active (1-3 days/week)',
  1.55: 'Moderately Active (3-5 days/week)',
  1.725: 'Very Active (6-7 days/week',
  1.9: 'Extra Active (Physical job)'
};

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  // Controllers
  TextEditingController ageController = TextEditingController();
  TextEditingController heightController = TextEditingController();
  TextEditingController weightController = TextEditingController();

  double? _selectedActivity;
  String _gender = "Male";

  void calculateAndGo() {
    if (ageController.text.isEmpty ||
        heightController.text.isEmpty ||
        weightController.text.isEmpty ||
        _selectedActivity == null) {
      return;
    }

    // Logic
    int age = int.parse(ageController.text);
    double height = double.parse(heightController.text);
    double weight = double.parse(weightController.text);

    double bmr;
    if (_gender == 'Male') {
      bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;
    } else {
      bmr = (10 * weight) + (6.25 * height) - (5 * age) - 161;
    }

    double tdee = bmr * _selectedActivity!;

    // Create the Data Object
    UserResult result = UserResult(
      calories: tdee,
      protein: ((tdee * 0.30) / 4).round(),
      carbs: ((tdee * 0.40) / 4).round(),
      fats: ((tdee * 0.30) / 9).round(),
    );

    // Navigate exactly like your example
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => DetailsPage(data: result),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Calorie Calculator"),
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            // Row for Gender
            Row(
              children: [
                Expanded(child: RadioListTile(title: Text("Male"), value: "Male", groupValue: _gender, onChanged: (v)=>setState(()=>_gender=v.toString()))),
                Expanded(child: RadioListTile(title: Text("Female"), value: "Female", groupValue: _gender, onChanged: (v)=>setState(()=>_gender=v.toString()))),
              ],
            ),

            // Row for Inputs
            Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: ageController,
                    keyboardType: TextInputType.number,
                    decoration: const InputDecoration(labelText: "Age"),
                  ),
                ),
                const SizedBox(width: 10),
                Expanded(
                  child: TextField(
                    controller: weightController,
                    keyboardType: TextInputType.number,
                    decoration: const InputDecoration(labelText: "Weight (kg)"),
                  ),
                ),
              ],
            ),

            const SizedBox(height: 10),

            TextField(
              controller: heightController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(labelText: "Height (cm)"),
            ),

            const SizedBox(height: 10),

            // Dropdown exactly like yours
            DropdownButton<double>(
              value: _selectedActivity,
              hint: const Text("Select Activity"),
              isExpanded: true,
              items: _activityValues.map((val) => DropdownMenuItem(
                value: val,
                child: Text(_activityLabels[val]!),
              )).toList(),
              onChanged: (value) {
                setState(() {
                  _selectedActivity = value;
                });
              },
            ),

            const SizedBox(height: 20),

            ElevatedButton(
              onPressed: calculateAndGo,
              child: const Text("Calculate"),
            ),
          ],
        ),
      ),
    );
  }
}