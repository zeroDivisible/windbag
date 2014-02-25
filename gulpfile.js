var gulp = require('gulp');

var resourcesPath = 'core/src/main/resources/**';
var outpath = 'build/classes/production/core/';


gulp.task('copy', function() {
  gulp.src(resourcesPath)
  .pipe(gulp.dest(outpath));
});


gulp.task('default', function() {
  gulp.run('copy');

  gulp.watch(resourcesPath, function(event) {
    gulp.run('copy');
  });
});

