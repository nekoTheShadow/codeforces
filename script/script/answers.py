import pathlib

with (pathlib.Path(__file__).resolve().parent.parent / 'src' / 'answers.js').open('w')as f:
    f.write('const answers_tsv = `\n')

    root = pathlib.Path(__file__).resolve().parent.parent.parent
    for path in root.glob('[0-9]*/*'):
        contest_id = path.parent.name
        index = path.name
        f.write(f'{contest_id}\t{index}\n')
    
    f.write('`\n')
    f.write('export default answers_tsv')


