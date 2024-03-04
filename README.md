# pokedex
### Seting up commands
- python -m venv env
- .\env\Scripts\activate
- git clone --depth 1 --progress --verbose https://github.com/veekun/pokedex.git
- cd .\pokedex\
- ENABLE CLOUDFARE WARP!!
- python setup.py develop
- pip install pymysql
- pip install cryptography
- `create database pokemon;` in MySQL
- `pokedex load -e mysql+pymysql://{USERNAME}:{PASSWORD}@localhost:{PORTNUMBER}/pokemon` for me it was `pokedex load -e mysql+pymysql://quick_one:{PASSWORD}@localhost:3306/pokemon`