
## Getting started: 20 minutes to Sematch

### Install Sematch

You need to install scientific computing libraries **numpy** and **scipy** first. An example of installing them with pip is shown below.

```
pip install numpy scipy
```

Depending on different OS, you can use different ways to install them. After sucessful installation of **numpy** and **scipy**, you can install sematch with following commands.

```
pip install sematch
python -m sematch.download
```

Alternatively, you can use the development version to clone and install Sematch with setuptools. We recommend you to update your pip and setuptools.

```
git clone https://github.com/gsi-upm/sematch.git
cd sematch
python setup.py install
```

To compute UMLS similariy, download MRCONSO.RRF and MRREL.RRF and place them into the dataset directory like:
```dataset/umls/MRCONSO.RRF```
```dataset/umls/MRREL.RRF```
See example in ```umls_example.py```