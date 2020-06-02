
// on draggin
function _allowDrop(event) {
  event.preventDefault();

  $('.ondrop').each(function () { $(this).removeClass('ondrop'); });
  $('#' + event.target.id).addClass('ondrop');
}

// on drag start
function _drag(event) {
  event.dataTransfer.setData("text", event.target.id);
  $('#' + event.target.id).addClass('ondragg');
}

// on drop
function _drop(event) {
  event.preventDefault();

  $('.ondrop').each(function () { $(this).removeClass('ondrop'); });
  $('.ondragg').each(function () { $(this).removeClass('ondragg'); });
  var dragged = $('#' + event.dataTransfer.getData("text"));
  var target = $('#' + event.target.id);
    
  if (dragged.attr('class') != target.attr('class'))
    return;

  var originalHtml = dragged.html();
  var targetHtml = target.html();

  target.html(originalHtml);
  dragged.html(targetHtml);
}

function unhideElement()
{
  var unhideAll = (_displayElements.length == 1 && _displayElements[0] == 0);

  $('.videoContent').each(function () {

    if (unhideAll) {
      $(this).removeClass('videoContent_hidden');
      return;
    }

    var id = parseInt($(this).attr('contentid'));
    if (_displayElements.indexOf(id) > -1)
      $(this).removeClass('videoContent_hidden');

  });
}

function updateNewSortContent()
{
  var newMax = parseInt($("#sortContentGroup").val());
  var groupID = 1;
  var itemsInGroup = 0;

  var html = "";
  $('.videoContentWrap').each(function () {
    if(itemsInGroup == 0)
      html += "<div class=\"videoContentGroup\" id=\"g" + groupID + "\" ondrop=\"_drop(event)\" ondragover=\"_allowDrop(event)\" draggable=\"true\" ondragstart=\"_drag(event)\">";

    itemsInGroup++;

    html += "<div class=\"videoContentWrap\">" + $(this).html() + "</div>";

    if (itemsInGroup == newMax) {
      itemsInGroup = 0;
      groupID++;
      html += "</div>";
    }
  });

  if (itemsInGroup != newMax) 
    html += "</div>";
  
  $('#contentBase').html(html);
}

function buttonUpdateSort() {
  var btn = $('#btnUpdateSort');
  if (btn.attr('inuse') == 'true')
    return;

  visual.Confirm('Are you sure you want to make this change?', 'Yes', 'No', function () {
    var data = '';
    $('.videoContent').each(function () {
      if ($(this).hasClass('videoContent_hidden'))
        return;

      if (data != '') data += ',';
      data += $(this).attr('contentid');
    });

    var btnText = btn.text();
    btn.attr('inuse', 'true');
    btn.text('Wait..');

    //string action, string id, string contentGroupID, string data, string separator)
    template.call('ApiSortContent', { action: 'add', id: _templateServiceContentSortID, contentGroupID: contentGroupID, data: data, separator: ',' }, function (response) {
      visual.Alert(response.message);
      _templateServiceContentSortID = response.id;

      btn.attr('inuse', 'false');
      btn.text(btnText);
    });

  });
}

function buttonDeleteSort() {
  var btn = $('#btnDeleteSort');
  if (btn.attr('inuse') == 'true')
    return;

  visual.Confirm('Are you sure you want to delete this sorting?', 'Yes', 'No', function () {
    var btnText = btn.text();
    btn.attr('inuse', 'true');
    btn.text('Wait..');

    //string action, string id, string contentGroupID, string data, string separator)
    template.call('ApiSortContent', { action: 'remove', id: _templateServiceContentSortID, contentGroupID: '', data: '', separator: '' }, function (response) {
      visual.Alert(response.message);
      _templateServiceContentSortID = response.id;

      btn.attr('inuse', 'false');
      btn.text(btnText);
    });

  });
}