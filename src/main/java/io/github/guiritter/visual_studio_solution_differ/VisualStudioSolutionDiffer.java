package io.github.guiritter.visual_studio_solution_differ;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VisualStudioSolutionDiffer {

	private static JTextArea sourceArea;
	private static JTextArea targetArea;
	private static JTextArea resultArea;

	public static final int INVALID_INDEX = -1;

	static {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
	}

	private static final List<Project> parse(String text) {
		var array = text.split("\n");
		int length = array.length;
		int i;
		String line0 = null;
		String line1 = null;
		String line2 = null;
		String line3 = null;
		int modulo;
		var list = new LinkedList<Project>();
		String line;
		for (i = 0; i < length; i++) {
			modulo = i % 4;
			line = array[i].split(" = ")[1];
			switch (modulo) {
				case 0 -> { line0 = line; }
				case 1 -> { line1 = line; }
				case 2 -> { line2 = line; }
				case 3 -> { line3 = line; list.add(new Project(line0, line1, line2, line3)); }
			}
		}
		return list;
	}

	private static final void removeDuplicates(List<Project> projectList) {
		int i, j;
		for (i = 0; i < (projectList.size() - 1); i++) {
			for (j = i + 1; j < projectList.size(); j++) {
				if (Objects.equals(projectList.get(i), projectList.get(j))) {
					projectList.remove(j);
					i--;
					break;
				}
			}
		}
	}

	private static final void merge(ActionEvent event) {

		var sourceList = VisualStudioSolutionDiffer.parse(sourceArea.getText());
		var targetList = VisualStudioSolutionDiffer.parse(targetArea.getText());

		VisualStudioSolutionDiffer.removeDuplicates(sourceList);
		VisualStudioSolutionDiffer.removeDuplicates(targetList);

		var resultBuilder = new StringBuilder();
		resultBuilder.append("lines only in left:\n\n");

		sourceList.forEach(sourceProject -> {
			if (targetList.stream().anyMatch(targetProject -> sourceProject.equals(targetProject))) {
				return;
			}
			resultBuilder.append(sourceProject.line0).append("\n");
		});
		
		resultBuilder.append("\nlines only in right:\n\n");

		targetList.forEach(targetProject -> {
			if (sourceList.stream().anyMatch(sourceProject -> targetProject.equals(sourceProject))) {
				return;
			}
			resultBuilder.append(targetProject.line0).append("\n");
		});

		resultArea.setText(resultBuilder.toString());
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("Visual Studio Solution Differ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane sourcePane = new JScrollPane();
		frame.add(sourcePane, BorderLayout.LINE_START);

		sourceArea = new JTextArea();
		sourcePane.setViewportView(sourceArea);
		sourceArea.setRows(10);
		sourceArea.setColumns(25);

		JScrollPane resultPane = new JScrollPane();
		frame.add(resultPane, BorderLayout.CENTER);

		resultArea = new JTextArea();
		resultPane.setViewportView(resultArea);
		resultArea.setRows(10);
		resultArea.setColumns(25);

		JScrollPane targetPane = new JScrollPane();
		frame.add(targetPane, BorderLayout.LINE_END);

		targetArea = new JTextArea();
		targetPane.setViewportView(targetArea);
		targetArea.setRows(10);
		targetArea.setColumns(25);

		JButton button = new JButton("format");
		frame.add(button, BorderLayout.PAGE_END);
		button.addActionListener(VisualStudioSolutionDiffer::merge);

		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
}
