import glob
import os


all_files = map(
    lambda path: path if (os.path.isfile(path)
        and not (path in ['./', './run.py'])) else None,
        glob.glob('./**', recursive=True)
)


with open('./transparencia-educativa.tex', mode='w') as fl_wt:
    for fl in all_files:
        if fl:
            print(fl)
            fl_wt.write(f'\\begin{{lstlisting}}[caption={fl}]\n')
            with open(fl) as fl_content:
                rd = fl_content.read()
                print(rd)
                fl_wt.write(rd)
            fl_wt.write('\end{lstlisting}\n\n')
