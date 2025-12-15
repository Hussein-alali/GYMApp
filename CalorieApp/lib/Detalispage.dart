import 'package:flutter/material.dart';
import 'main.dart';

class DetailsPage extends StatelessWidget {
  final UserResult data;

  const DetailsPage({super.key, required this.data});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Your Result"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text("Maintenance Calories:", style: TextStyle(fontSize: 20)),
            Text(
              "${data.calories.round()} kcal",
              style: const TextStyle(fontSize: 40, fontWeight: FontWeight.bold, color: Colors.deepPurple),
            ),
            const SizedBox(height: 30),

            // Simple Cards for Macros
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                _buildInfo("Protein", "${data.protein}g"),
                _buildInfo("Carbs", "${data.carbs}g"),
                _buildInfo("Fats", "${data.fats}g"),
              ],
            )
          ],
        ),
      ),
    );
  }

  Widget _buildInfo(String label, String value) {
    return Column(
      children: [
        Text(label, style: const TextStyle(fontWeight: FontWeight.bold)),
        Text(value, style: const TextStyle(fontSize: 18)),
      ],
    );
  }
}