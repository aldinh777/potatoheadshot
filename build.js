const fs = require('fs');
const path = require('path');

const gradle_build = fs.readFileSync('build.gradle', 'utf8');

const mc_version = '1.12.2';
const version = /version = '(.+)'/g.exec(gradle_build)[1];
const project_name = /archivesBaseName = '(.+)'/g.exec(gradle_build)[1];

const filename = jarify(project_name, version);
const output_dir = path.join(process.env.appdata, '.minecraft/mods');
const output = jarify(project_name, version, mc_version);

const source = path.resolve(__dirname, './build/libs', filename);
const destination = path.resolve(output_dir, output);

function copyBuildFile () {
  fs.copyFile(source, destination, (err) => {
    if (err) {
      throw err;
    }
    const random = Math.floor(Math.random() * 100) % 3;
    switch (random) {
      case 0:
        console.log(output + ' copied successfully');
        break;
      case 1:
        console.log(output + ' copied gracefully with style');
        break;
      case 2:
        console.log('the file ' + output + ' has been done now try it');
    }
  });
}

function jarify (...names) {
  let result = '';
  for (let i = 0; i < names.length; i++) {
    result += names[i];
    if (i < names.length - 1) {
      result += '-';
    }
  }
  return result + '.jar';
}

copyBuildFile();
