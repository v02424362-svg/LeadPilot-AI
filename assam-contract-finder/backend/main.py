from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import requests
from bs4 import BeautifulSoup
from datetime import datetime

app = FastAPI(title='Assam Contract Finder API')
app.add_middleware(CORSMiddleware, allow_origins=['*'], allow_methods=['*'], allow_headers=['*'])
ASSAM_URL='https://assamtenders.gov.in/'

def fetch_latest():
    r=requests.get(ASSAM_URL, timeout=20, headers={'User-Agent':'AssamContractFinder/1.0'})
    r.raise_for_status()
    soup=BeautifulSoup(r.text,'lxml')
    rows=[]
    for tr in soup.select('table tr'):
        cells=[c.get_text(' ',strip=True) for c in tr.select('th,td')]
        if len(cells)>=4 and cells and 'Tender Title' not in ' '.join(cells): rows.append(cells)
    return rows[:50]

@app.get('/health')
def health(): return {'ok':True}

@app.get('/tenders')
def tenders(q:str='', category:str='All'):
    try:
        out=[]
        for row in fetch_latest():
            text=' '.join(row)
            if q and q.lower() not in text.lower(): continue
            if category!='All' and category.lower() not in text.lower(): continue
            out.append({'title':row[0] if row else 'Tender','raw':row,'source':'Assam eProcurement','url':ASSAM_URL})
        return {'updated':datetime.utcnow().isoformat()+'Z','items':out}
    except Exception as e:
        return {'updated':None,'items':[],'error':str(e)}

@app.get('/sources')
def sources():
    return [{'name':'Assam eProcurement','url':ASSAM_URL},{'name':'Indian Railways IREPS','url':'https://www.ireps.gov.in/'}]
