function ProviderNotesManager()
{
  this.init = function () {
    this.btnAddElements();
    this.btnAppend();
    this.btnDelete();
    this.btnSubmit();
  }

  // SUMMARY: Append body of the note
  this.btnAppend = function () {
    $('body').on('click', '.pn_open', function () {
      var note = $(this).closest('.provider_note');
      if(note.hasClass('provider_note_closed'))
      {
        note.removeClass('provider_note_closed');
        $(this).text('▲');
      }
      else
      {
        note.addClass('provider_note_closed');
        $(this).text('▼');
      }
    });
  }

  // SUMMARY: Remove note button listener
  this.btnDelete = function () {
    $('body').on('click', '.pn_delete', function () {
      var note = $(this).closest('.provider_note');
      visual.Confirm('Are you sure you want to delete this note?', 'Yes', 'No', function () {
        note.remove();
      });
    });

    $('body').on('dblclick', '.provider_separator', function () {
      var separator = $(this);
      separator.remove();
    });
  }

  // SUMMARY: Add new note button listener
  this.btnAddElements = function()
  {
    $('.btnAddNote').click(function () {
      var masterHtml = $('.masterNote').html();
      $('#notes_container').append(masterHtml);
    });

    $('.btnAddSeparator').click(function () {
      var masterHtml = $('.masterSeparator').html();
      $('#notes_container').append(masterHtml);
    });
  }

  // SUMMARY: Submit all changes made for this provider/country
  this.btnSubmit = function () {
    var self = this;
    $('.btnSubmit').click(function () {
      var btn = $(this);
      visual.Confirm('Are you sure you want to save this changes?', 'Yes', 'No', function () {
        var data = self.collectData();
        template.call('ApiUpdateProviderNote', { data: data }, function (response) {
          visual.Alert(response.message);
        });
      });
    });
  }

  // SUMMARY: Collect all data for database storage
  this.collectData = function () {
    var data = "";
    $('#notes_container').children().each(function () {
      if (data != '') data += '|';

      if ($(this).hasClass('provider_separator'))
        data += '-';

      if (!$(this).hasClass('provider_note'))
        return;

      var title = $(this).find('.pn_title').val().replace('|', '').replace('#', '');
      var text = $(this).find('.pn_text').val().replace('|', '').replace('#', '');

      data += title + '#' + text;
    });
    return data;
  }

  this.init();
}