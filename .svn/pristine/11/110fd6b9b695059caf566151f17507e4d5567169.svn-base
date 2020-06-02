function Automation()
{
  this.init = function()
  {
    this.addElementBtn();
    this.btnDelete();
    this.btnUpdate();

    this.addGroupElement();
    this.btnGroupUpdate();
    this.btnGroupDelete();
  }

  // Automation
  this.addElementBtn = function()
  {
    $('.btnAddElement').click(function () {
      var html = $('#block_template').html();
      $('#template_container').append(html);
    });
  }

  // Automation
  this.btnUpdate = function()
  {
    $('body').on('click', '.btnUpdate', function () {
      var automation = $(this).closest('.automationConfiguration');
      var automationID = automation.attr('automationid');
      var data =
      {
        ID: automationID,
        Name: automation.find('.automatizationName').val(),
        TransactionLimit: automation.find('.automationTransactionLimit').val(),
        ExternalOfferName: automation.find('.automationExternalOffername').val(),
        MobileOperator: automation.find('.selectMobileOperator option:selected').attr('value'),
        AutomationGroup: automation.find('.selectAutomationGroup option:selected').attr('value'),
        Comment: automation.find('.automationComment').val(),
        IsActive: automation.find('.automationIsActive').is(':checked') ? '1' : '0'
      };

      template.call('ApiUpdateAutomationConfiguation', data, function (response) {
        if (response.status) {
          visual.Toast(response.message);
          automation.find('.automatizationHead').text('Automatization entry');
          automation.attr('automationid', response.id);
        }
        else
          visual.Alert(response.message);
      });

    });
  }

  // Automation
  this.btnDelete = function()
  {
    $('body').on('click', '.btnDelete', function () {
      var automation = $(this).closest('.automationConfiguration');
      var automationID = automation.attr('automationid');
      visual.Confirm('Are you sure you want to delete this configuration ?', 'Yes', 'No', function () {
        if (automationID == "-1")
          automation.remove();
        else
          template.call('ApiDeleteAutomationConfiguration', { AutomationID: automationID }, function (response) {
            if (!response.status)
              visual.Alert(response.message);
            else
            {
              automation.remove();
              visual.Toast(response.message);
            }
          });
      });
    });
  }




  // Automation group
  this.addGroupElement = function()
  {
    $('.btnGroupAddElement').click(function () {
      var html = $('#block_template').html();
      $('#template_container').append(html);
    });
  }

  // Automation group
  this.btnGroupDelete = function()
  {
    $('body').on('click', '.btnGroupDelete', function () {
      var automation = $(this).closest('.automationConfiguration');
      var automationID = automation.attr('automationgroupid');
      visual.Confirm('Are you sure you want to delete this configuration group ?', 'Yes', 'No', function () {
        if (automationID == "-1")
          automation.remove();
        else
          template.call('ApiDeleteAutomationGroupConfiguration', { AutomationID: automationID }, function (response) {
            if (!response.status)
              visual.Alert(response.message);
            else {
              automation.remove();
              visual.Toast(response.message);
            }
          });
      });
    });
  }

  // Automation group
  this.btnGroupUpdate = function()
  {
    var self = this;
    $('body').on('click', '.btnGroupUpdate', function () {
      var automation = $(this).closest('.automationConfiguration');
      var automationID = automation.attr('automationgroupid');
      var startTime = automation.find('.automatizationStartTime').val();
      var endTime = automation.find('.automatizationEndTime').val();

      if (self.checkGroupStartEndDate(startTime, endTime))
      {
        visual.Alert("Error with StartTime or EndTime!");
        return;
      }

      var orderData = self.groupCollectData(automation);
      var data =
      {
        Name: automation.find('.automatizationName').val(),
        ID: automationID,
        RotationTime: automation.find('.automatizationRotation').val(),
        externalCleanOffer: automation.find('.automatizationCleanOffer').val(),
        StartTime: startTime, 
        EndTime: endTime,
        Data: orderData,
        AutoStart: automation.find('.automatizationAutoStart').is(':checked') ? '1' : '0',
        IsActive: automation.find('.automationIsActive').is(':checked') ? '1' : '0'
      };

      template.call('ApiUpdateAutomationGroupConfiguration', data, function (response) {
        if (response.status) {
          visual.Toast(response.message);
          automation.find('.automatizationHead').text('Automatization group');
          automation.attr('automationgroupid', response.id);
        }
        else
          visual.Alert(response.message);
      });

    });
  }

  // Automation group
  this.checkGroupStartEndDate = function(startdate, enddate)
  {
    var startdateparts = startdate.split(':');
    if (startdateparts.length != 2)
      return true;
    if (!$.isNumeric(startdateparts[0]) || !$.isNumeric(startdateparts[1]))
      return true;
    if (parseInt(startdateparts[0]) < 0 || parseInt(startdateparts[0]) > 24)
      return true;
    if (parseInt(startdateparts[1]) < 0 || parseInt(startdateparts[1]) > 60)
      return true;

    var enddateparts = enddate.split(':');
    if (enddateparts.length != 2)
      return true;
    if (!$.isNumeric(enddateparts[0]) || !$.isNumeric(enddateparts[1]))
      return true;
    if (parseInt(enddateparts[0]) < 0 || parseInt(enddateparts[0]) > 24)
      return true;
    if (parseInt(enddateparts[1]) < 0 || parseInt(enddateparts[1]) > 60)
      return true;


    return false;
  }

  // Automation group
  this.groupCollectData = function(group) {
    var data = '';
    group.find('.service').each(function () {
      if (data != '') data += '#';
      data += $(this).attr('automationID') + '|' + $(this).attr('orderID');
    });

    return data;
  }

  this.init();
}