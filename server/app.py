import subprocess
import os

if __name__ == "__main__":
    script_path = "models/Users.model.py"
    command = ["python", script_path]

    try:
        subprocess.run(command, check=True, text=True, cwd=os.path.dirname(os.path.abspath(__file__)))
    except subprocess.CalledProcessError as e:
        print(f"Test execution failed with error: {e}")
