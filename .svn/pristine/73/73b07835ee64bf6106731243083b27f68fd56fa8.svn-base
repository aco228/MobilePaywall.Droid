function FooterConfiguration() {
  this._init = function () {
    this.onCheckbox();
    this.prepareLoadedFooterLinks();
    this.btnFooterRemove();
    this.btnAddNewElement();
    this.btnSubmit();
  }
  
  // SUMMARY: On laod, select options in .cshtml
  this.prepareLoadedFooterLinks = function () {
    var self = this;
    $('.footerElements .footerElement select').each(function () {
      var element = $(this).closest('.footerElementTab');
      var selectedID = parseInt($(this).attr('selectedid'));
      $(this).find('option[value=' + selectedID + ']').attr('selected', 'selected');

      var input = element.find('.inputText');
      if (typeof input !== 'undefined' && typeof input.val() !== 'undefined' && input.val() != '' && element.find('.checkbox2').length == 1)
        element.find('.checkbox2').trigger('change');
    });
  }
    
  // SUMMARY: Button to add new element to container
  this.btnAddNewElement = function () {
    $('.btnAddFooterElement').click(function () {
      var container = $(this).closest('.md-card').find('.footerElements');
      var templateHtml = $('#templateFooterElement').html();
      container.append(templateHtml);
    });
  }

  // SUMMARY: Button to remove footer 
  this.btnFooterRemove = function () {
    $('body').on('click', '.footerRemove', function () {
      var element = $(this).closest('.footerElement').remove();
    });
  }

  // SUMMARY: on change checkbox
  this.onCheckbox = function () {
    $('body').on('change', '.fCheckbox', function () {
      var parent = $(this).parent();
      parent.find('.inputSelected').removeClass('inputSelected');

      if ($(this).hasClass('checkbox1')) {
        parent.find('.inputSelect').addClass('inputSelected');
        $(this).prop('checked', true);
        parent.find('.checkbox2').prop('checked', false);
      }
      else if ($(this).hasClass('checkbox2')) {
        parent.find('.inputText').addClass('inputSelected');
        $(this).prop('checked', true);
        parent.find('.checkbox1').prop('checked', false);
      }
    });
  }
  
  // SUMMARY: Save footer data country specific
  this.btnSubmit = function () {
    var self = this;
    $('.btnSubmit').click(function () {
      if ($(this).attr('inuse') == 'true') return;
      var btn = $(this);
      var originalText = btn.text();

      visual.Confirm('Are you sure you want to change ?', 'Yes', 'No', function () {
        var data = self.collectData(btn.closest('.md-card').find('.footerElements'));
        btn.attr('inuse', 'true'); btn.text('Wait..');
        template.call('ApiAddNewFooterLinks', { data: data }, function (response) {
          visual.Alert(response.message);
          btn.attr('inuse', 'false');
          btn.text(originalText);
        });
      });
    });
  }

  // SUMMARY: Collect data for send
  this.collectData = function (contentBase) {
    var data = [];
    var action = contentBase.attr('action');
    contentBase.find('.footerElement').each(function () {
      var dataBase =
      {
        Action: action,
        SubAction: 'Add',
        FooterLinkID: -1,
        RouteID: -1,
        TranslationGroupID: -1,
        InputLink: '',
        InputText: '',
        MobileOperatorID: -1,
        PaymentProviderID: -1,
        IfHasPayment: -1
      };

      dataBase.FooterLinkID = $(this).attr('footerLinkID');

      if ($(this).find('.footerElementTabLink > .checkbox1').is(':checked'))
        dataBase.RouteID = $(this).find('.routeSelect option:selected').attr('value');
      else
        dataBase.InputLink = $(this).find('.routeInput').val();

      if ($(this).find('.footerElementTabText > .checkbox1').is(':checked'))
        dataBase.TranslationGroupID = $(this).find('.translationGroupSelect option:selected').attr('value');
      else
        dataBase.InputText = $(this).find('.textInput').val();

      dataBase.MobileOperatorID = $(this).find('.operatorSelect option:selected').attr('value');

      if (action == 'Provider')
        dataBase.PaymentProviderID = _providerID;

      dataBase.IfHasPayment = $(this).find('.hasPayment option:selected').attr('value');

      data.push(dataBase);
    });

    if (data.length == 0)
      data.push({
        Action: action,
        SubAction: 'Remove',
        FooterLinkID: -1,
        RouteID: -1,
        TranslationGroupID: -1,
        InputLink: '',
        InputText: '',
        MobileOperatorID: -1,
        PaymentProviderID: _providerID,
        IfHasPayment: -1
      });

    return data;
  }
  
  this._init();
}