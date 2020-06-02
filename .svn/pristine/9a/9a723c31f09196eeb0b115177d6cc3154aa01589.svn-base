function Application()
{
  this.init = function()
  {
    this.logout();
  }

  this.logout = function()
  {
    $('#btnLogout').click(function (event) {
      event.preventDefault();
      var url = $(this).attr('href');

      visual.Confirm('Are you sure you want to logout?', 'Yes', 'No', function () {
        window.location = url;
      });

    });
  }

  this.init();
}

