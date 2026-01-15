import requests
from bs4 import BeautifulSoup
from urllib.parse import urljoin

url = "https://example.com"

res = requests.get(url, timeout=10)
res.raise_for_status()

soup = BeautifulSoup(res.text, "html.parser")

links = set()

for a in soup.find_all("a", href=True):
    full_url = urljoin(url, a["href"])
    links.add(full_url)

for link in links:
    print(link)

