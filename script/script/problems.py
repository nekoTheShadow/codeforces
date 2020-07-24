import json, urllib.request, pathlib

def get_results(methodname):
    url = f'https://codeforces.com/api/{methodname}'
    request = urllib.request.Request(url)
    with urllib.request.urlopen(request) as response:
        return json.loads(response.read().decode("utf-8"))['result']


contests = {result['id'] : result for result in get_results('contest.list')}
rows = []
for result in get_results('problemset.problems')['problems']:
    contest_id = result['contestId']
    contest_name= contests[contest_id]['name']
    index = result['index']
    url = f'https://codeforces.com/contest/{contest_id}/problem/{index}'

    rows.append({
        'contest_id'   : contest_id,
        'contest_name' : contest_name, 
        'index'        : index,
        'name'         : result['name'],
        'rating'       : result.get('rating', ' '),
        'url'          : url
    })

rows.sort(key=lambda h: (h['contest_id'], h['index']))

fieldnames = [
    'contest_id',
    'contest_name',
    'index',
    'name',
    'rating',
    'url',
]

with (pathlib.Path(__file__).resolve().parent.parent / 'src' / 'problems.js').open('w')as f:
    f.write('const problems_tsv = `\n')

    for row in rows:
        line = '\t'.join(str(row[fieldname]).replace('`', '\\`') for fieldname in fieldnames)
        f.write(f'{line}\n')
    
    f.write('`\n')
    f.write('export default problems\n')
