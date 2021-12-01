package io.github.guiritter.visual_studio_solution_differ;

public final class Project {

	public static final String labelA = "SccProjectUniqueName";
	public static final String labelB = "SccProjectTopLevelParentUniqueName";
	public static final String labelC = "SccProjectName";
	public static final String labelD = "SccLocalPath";

	public final String line0;
	public final String line1;
	public final String line2;
	public final String line3;

	public int index = VisualStudioSolutionDiffer.INVALID_INDEX;

	public Project(String line0, String line1, String line2, String line3) {
		this.line0 = line0;
		this.line1 = line1;
		this.line2 = line2;
		this.line3 = line3;
	}

	public Project(Project project) {
		this(project.line0, project.line1, project.line2, project.line3);
	}

	@Override
	public String toString() {
		return "\t\t" + labelA + index + " = " + line0 + "\n"
			+ "\t\t" + labelB + index + " = " + line1 + "\n"
			+ "\t\t" + labelC + index + " = " + line2 + "\n"
			+ "\t\t" + labelD + index + " = " + line3;
	}

	@Override
	public boolean equals(Object obj) {

		if ((obj == null) || (!(obj instanceof Project))) {
			return false;
		}

		var other = (Project) obj;

		return (line0.compareTo(other.line0) == 0) && (line1.compareTo(other.line1) == 0) && (line2.compareTo(other.line2) == 0) && (line3.compareTo(other.line3) == 0);
	}
}
