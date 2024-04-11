# pokedex
### Seting up commands
- python -m venv env
- .\env\Scripts\activate
- git clone --depth 1 --progress --verbose https://github.com/veekun/pokedex.git
- cd .\pokedex\
- ENABLE CLOUDFARE WARP!!
- ~~python setup.py develop~~
- pip install pymysql
- pip install cryptography
- ~~`create database pokemon;` in MySQL~~
- ~~`pokedex load -e mysql+pymysql://{USERNAME}:{PASSWORD}@localhost:{PORTNUMBER}/pokemon` for me it was `pokedex load -e mysql+pymysql://quick_one:{PASSWORD}@localhost:3306/pokemon`~~
- cd ..
- execute `dll.sql`

### setting up environment variables
- pip install python-dotenv
- create a .env file in the root directory
- add the following lines to the .env file
```
DB_USER=quick_one
DB_PASSWORD=your_password
DB_HOST=localhost
DB_PORT=3306
DB_NAME=pokedex
SQLALCHEMY_SILENCE_UBER_WARNING=1
```

### importing the database to MySQL
- pip install pandas sqlalchemy pymysql
- pip install --force-reinstall 'sqlalchemy<2.0.0'
- pip install pandas==2.1.4
- run loader.ipynb