import json, urllib.request, csv

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

with open('result.csv', 'w') as f:
    fieldnames = [
        'contest_id',
        'contest_name',
        'index',
        'name',
        'rating',
        'url',
    ]
    writer = csv.DictWriter(f, fieldnames=fieldnames)
    writer.writeheader()
    writer.writerows(rows)
