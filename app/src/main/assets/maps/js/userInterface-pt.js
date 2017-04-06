(function($,global) {

/**
 * 
 */

    var kChooseItinerary = "Escolha o seu caminho";
    var kStart = "Iniciação";
    var kArrival = "Destino";
    var kEnablePRMMode = "Habilitar modo pessoas com deficiência";
    var kChooseStart = "Escolha o seu ponto de partida";
    var kChooseDestination = "Escolha o seu destino";
 
    var LOCALE_ES = {
        "Choose your itinerary": "Selecciona tu itinerario",
        "Start": "Inicio",
        "Arrival": "Destino",
        "Enable PRM mode": "Activar mode PRM",
        "Choose your starting point": "Selecciona el punto inicial",
        "Choose your destination": "Selecciona tu destino"
    };
 
    //var CURRENT_LOCALE = LOCALE_ES;
 
    /*function getText(string) {
        return CURRENT_LOCALE[string] || string;
    }*/

    var requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame
            || window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
    var cancelAnimationFrame = window.cancelAnimationFrame || window.mozCancelAnimationFrame;
    
    var ViewerBaseUi = function(settings) {
        this._cacheStore = {};
        this.params = $.extend({}, ViewerBaseUi.prototype.defaultParams, settings);
        this._currentDestination = null;
        this._currentShape = null;
        this.loadingInterval = null;
        this.interval = null;
    };
    
    /**
     * @type {Object}
     */
    var ToolbarPlacement = {
        NO_PLACEMENT: 0,
        LEFT_INSIDE: 1,
        LEFT_OUTSIDE: 2,
        RIGHT_INSIDE: 3,
        RIGHT_OUTSIDE: 4
    }

    ViewerBaseUi.prototype.defaultParams = {
        labelLoading : "loading",
        startingDestinationsCategories: [],
        endingDestinationsCategories: [],
        displayInfoPanel: true,
        displayItineraryPanel: true,
        allowChangeItineraryDestination: true,
        toolbarPlacement: 0,
        displayToolbar: true,
        displayPMR: true
    };

    ViewerBaseUi.prototype._waitingFloor = null;
    /**
     * 
     * @param {VDCanvasViewerBootstrap} app
     * @param dispatcher
     * @param {Object} params
     */
    ViewerBaseUi.prototype.register = function(app, dispatcher, params) {
        /**
         * @type {VDCanvasViewerBootstrap}
         */
        this.app = app;
//        dispatcher.bind('loadingQueueStart', $.proxy( this.loadingStart, this));
//        dispatcher.bind('loadingQueueFinished', $.proxy( this.loadingFinished, this));
        dispatcher.bind('vdInitializeContainer', $.proxy( this.initializeContainer, this));
        dispatcher.bind('vdShapePicked', $.proxy( this.shapePicked, this));
        dispatcher.bind('vdDestinationPicked', $.proxy( this.destinationPicked, this));
        dispatcher.bind('vdDestinationRollOver', $.proxy( this.destinationRollOver, this));
        dispatcher.bind('vdDestinationRollOut', $.proxy( this.destinationRollOut, this));
        dispatcher.bind('vdFloorChanged', $.proxy( this.floorChanged, this));
        dispatcher.bind('vdModelReady', $.proxy( this.modelReady, this));
        dispatcher.bind('vdPathCalculated', $.proxy( this.pathReady, this));
        dispatcher.bind('vdResize', $.proxy( this.resizeUi, this ));
        
        this.params = $.extend( true, {}, this.params, params );
        //if( app.params.selectedDestination ) this._currentDestination = app.params.selectedDestination;
        // handling parameters
        this.pathManager = app.getPathManager();
    };

    /*ViewerBaseUi.prototype.loadingStart = function(ev, settings) {
        if (this.loadingInterval != null)
            return;
        var self = this;
        this.destinationPanel.removeClass('close').addClass("loading");
        this.destinationPanel.trigger( "panelLoading", [this] );
        var num = 0;
        var c = function(time) {
            var label = Drupal.t( 'Loading', {}, {context: "viadirect_map_canvas"}) + " ";
            num = Math.min(++num, 4);
            if (num == 4)
                num = 0;
            for (var i = 0; i < num; i++)
                label = label + ".";
            self.setContent(label);
        }
        c(0);
        this.loadingInterval = setInterval(c, 200);

    };

    ViewerBaseUi.prototype.loadingFinished = function(ev, settings) {
        clearInterval(this.loadingInterval);
        this.loadingInterval = null;
        if( this.destinationPanel ) {
            this.destinationPanel.removeClass("loading");
        }
    };*/

    /**
     * initialize the container
     * 
     * @param Event
     *                ev
     * @param JQueryResultSet
     *                pContext
     */
    ViewerBaseUi.prototype.initializeContainer = function(ev, pContext) {
        this._ctx = $("<div class='vdUserInterface'></div>").appendTo(pContext);
        this.UiButtonBar = $('<div class="vdUiButtons"></div>');
        this.floorsMenu = $('<div class="vdChangeFloorButtons loading"></div>').appendTo( this.UiButtonBar );
        if( this.params.displayInfoPanel ) {
            this.destinationPanel = $("<div class='destinationInfoArea close'><div class='content-wrapper'></div></div>").appendTo(this._ctx);
        }
        this.zoomControls = $(
                '<ul class="vdZoomButtons"><li class="vdMapButton vdMapZoom" data-action="vdUIZoomIn" data-pressable="1" title="Zoom in"><span>+</span></li><li class="vdMapButton vdMapZoom"  data-pressable="1" data-action="vdUIZoomOut" title="Zoom out"><span>-</span></li> '+ ( this.params.displayItineraryPanel ? '<li class="vdMapButton showItinerary" data-toggle="1" title="Show itinerary">&nbsp;</li>' : '' ) + '</ul>').appendTo( this.UiButtonBar );
        this.loaderLayer = $('<div class="vdLoader"></div>').appendTo(pContext);

        if( this.params.displayToolbar ) {
            this.UiButtonBar.appendTo(this._ctx);
        }
        
        $('.vdMapButton.showItinerary', this.zoomControls.get(0)).click($.proxy(this.showItineraryPanel, this));
        
        this.canvasElement = pContext.find(' > canvas.vdMap ');
    };

    ViewerBaseUi.prototype._filterDestinationHasShape = function(ele) {
        return "ShapeList" in ele && ele.ShapeList.length;
    };
    
    /**
     * 
     * @param ev
     */
    ViewerBaseUi.prototype._buildItineraryPanel = function() {
        var i, len, category, m, key;
        var startingDestinations = [];
        m = this.model.ViadirectMap;

        //Variables para redimensional el panel de itinerario
        var anchoCustom = $(window).width()/3;
        var altoCustom = $(window).height()/3;
        var stringAnchoCustom = anchoCustom+"px";
        var stringAltoCustom = altoCustom+"px";
        var stringMarginTopCustom = "-"+altoCustom/2+"px";
        
        if( this.params.startingDestinationsCategories.length ) {
            for ( key in this.params.startingDestinationsCategories ) {
                if( this.params.startingDestinationsCategories[key] != 0 ) {
                    category = m.FindCategoryByName( key );
                    if ( category )
                        startingDestinations = startingDestinations.concat( startingDestinations, m.FindDestinationsByCategory( category.Name ) );
                }
            }
        } else {
            startingDestinations = this.model.ViadirectMap.DestinationList;
        }
        startingDestinations = startingDestinations.filter(function(ele){ return ele;});
        startingDestinations = startingDestinations.filter( this._filterDestinationHasShape );
        startingDestinations.sort( function(a,b){ return a.NameTranslations[0] > b.NameTranslations[0] ? 1 : -1 });

        var endingDestinations = [];
        var categories = [ 'stores', 'confort' ];
        if( this.params.endingDestinationsCategories.length ) {
            for ( key in this.params.endingDestinationsCategories) {
                if( this.params.endingDestinationsCategories[key] != 0 ) {
                    category = m.FindCategoryByName( this.params.endingDestinationsCategories[key] );
                    if( category ) {
                        endingDestinations = endingDestinations.concat( m.FindDestinationsByCategory( category.Name ) );
                    }
                }
            }
        } else {
            endingDestinations = this.model.ViadirectMap.DestinationList;
        }
        
        endingDestinations = endingDestinations.filter( this._filterDestinationHasShape );
        endingDestinations = $.unique( endingDestinations );
        endingDestinations.sort( function(a,b){ return a.NameTranslations[0] > b.NameTranslations[0] ? 1 : -1 });
        
        var startOptStr = "<option>" + kChooseStart + "</option>";
        for (i = 0, len = startingDestinations.length; i < len; i++) {
            startOptStr += "<option value='" + startingDestinations[i].Id + "'>" + startingDestinations[i].NameTranslations[0] + "</option>";
        }
        var endDestinationStr = "";
        if( this.params.allowChangeItineraryDestination) {
            var endOptStr = "<option>" + kChooseDestination + "</option>";
            for (i = 0, len = endingDestinations.length; i < len; i++) {
                endOptStr += "<option value='" + endingDestinations[i].Id + "'>" + endingDestinations[i].NameTranslations[0] + "</option>";
            }
            endDestinationStr = '<select class="value" name="end-destination">' + endOptStr + '</select>';
        } else {
            endDestinationStr = '<span class="value">' + this.model.ViadirectMap.FindDestinationById( this._currentDestination ).NameTranslations[0] +'</span>';
        }
        var tpl = '<div class="itineraryPanel" style="width:'+stringAnchoCustom+';height:'+stringAltoCustom+'; position:absolute; margin:auto; top:50%; margin-top:'+stringMarginTopCustom+'; right:0; bottom:0; left:0;"><a href="#" class="close-button">x</a><h1>' + kChooseItinerary + '</h1><div><div><span class="label">' + kStart + '</span><select class="value" name="start-destination">' + startOptStr + '</select></div> <div><span class="label">' + kArrival + '</span>' + endDestinationStr +' </div>';
        
        if( this.params.displayPMR && this.model.ViadirectMap.FloorList.length > 1) {
            // pmr 
            tpl += '<div class="pmr-row"><input type="checkbox" name="pmr" title="Enable PRM mode" />' + kEnablePRMMode + '</div>';
        }
        
        //btn btn mostrar, para hacer la búsqueda solo apretando el boton y no siempre que se elija el selector
        tpl+='<div class="btn-cerrar-div"><input type="button" value="Mostrar" id="btn-mostrar-itinerario" name="btn-mostrar-itinerario" /></div>';

        tpl += '</div></div>';
        this.itinerarySelectorPanel = $(tpl);

        //this.itinerarySelectorPanel.on('change', 'select', $.proxy(this.itinerarySelectChange, this));
        this.itinerarySelectorPanel.on('click', 'a.close-button', $.proxy(this.closeItineraryPanel, this));
        //this.itinerarySelectorPanel.on('change', ':input[name=pmr]', $.proxy( this.requestPmr, this));

        //Aplico estas acciones al botón
        this.itinerarySelectorPanel.on('click', ':input[name=btn-mostrar-itinerario]',$.proxy(this.itinerarySelectChange, this));
        this.itinerarySelectorPanel.on('click', ':input[name=btn-mostrar-itinerario]',$.proxy( this.requestPmr, this));
        //Cierra el panel tambien
        this.itinerarySelectorPanel.on('click', ':input[name=btn-mostrar-itinerario]',$.proxy(this.closeItineraryPanel, this));
    };

    ViewerBaseUi.prototype.requestPmr = function(ev) {
        console.log( ev.target.checked );
        this.app.getEventManager().trigger( "vdSetPMRMode", ev.target.checked );
    };
    
    ViewerBaseUi.prototype.closeItineraryPanel = function(ev) {
        this.itinerarySelectorPanel.detach();
        $('.vdMapButton.showItinerary').removeClass("selected").data('toggle', 1);
        if (ev) {
            ev.stopPropagation();
        }
        return false;
    };

    ViewerBaseUi.prototype.itinerarySelectChange = function(ev) {

        var startDestinationId = this.itinerarySelectorPanel.find('select[name="start-destination"] option:selected').val();
        var endDestinationId = this.params.allowChangeItineraryDestination ? this.itinerarySelectorPanel.find('select[name="end-destination"] option:selected').val() : this._currentDestination;

        // sending to app
        this.model.getEventManager().trigger('vdRequestItinerary', [ parseInt(startDestinationId), parseInt(endDestinationId) ]);
    };

    ViewerBaseUi.prototype.showItineraryPanel = function(ev) {
        if ($(ev.target).data('toggle') == 1) {
            if (!this.itinerarySelectorPanel)
                this._buildItineraryPanel();
            
            //Se añade al body para poder centrar el componente
            //this.itinerarySelectorPanel.appendTo(this._ctx);
            this.itinerarySelectorPanel.appendTo($(document.body));

            if (this._currentDestination) {
                this.itinerarySelectorPanel.find('select[name="end-destination"]').val(this._currentDestination);
            }
            $(ev.target).data('toggle', 0);
        } else {

            this.closeItineraryPanel(null);
            $(ev.target).data('toggle', 1);
        }
    };

    /**
     * 
     * @param {Event}
     *                ev
     * @param {MapManipulator}
     *                map
     */
    ViewerBaseUi.prototype.modelReady = function(ev, map) {

        this.model = map;
        var floorsMenu = $('<ul class="vdChangeFloorButtons"></ul>');
        var floorList = map.ViadirectMap.FloorList;
        for ( var key in floorList) {
            $(
                    '<li class="vdMapButton vdMapFloor '+ (this._waitingFloor == floorList[key].Name ? "vdActive" : "") + '" data-action="vdUIFloorChange" data-value="' + floorList[key].Name
                            + '" ><span>' + floorList[key].NameTranslations[0] + '</span></li>').appendTo(floorsMenu);
        }

        if (floorsMenu.children().length > 1) {
            floorsMenu.children('li').click($.proxy(this.floorButtonPicked, this));
            $('.vdChangeFloorButtons', this._ctx.get(0)).replaceWith(floorsMenu);
            this.floorsMenu = floorsMenu;
        } else {
            $('.vdChangeFloorButtons', this._ctx.get(0)).detach();
        }
        var self = this;
        var o = null;
        var callback = function(target, map) {
            return function(t) {
                if (o === null)
                    o = t;
                var delta = (t - o);

                var a = $(target).data('action');
                if ((delta == 0 || delta > 100) && a) {
                    map.getEventManager().trigger(a);
                    o = t;
                }
                self.interval = requestAnimationFrame(arguments.callee);
            };
        };
        if (requestAnimationFrame && cancelAnimationFrame) {
            var buttonPressed = false;

            $('.vdMapButton', this._ctx.get(0)).on( "mousedown touchstart", function(ev) {
                console.log(this);
                var $this = $(this);
                $this.addClass("selected");
                if ($this.data('pressable')) {
                    buttonPressed = $this;
                    cancelAnimationFrame(self.interval);
                    o = null;

                    self.interval = requestAnimationFrame(callback(this, map));
                } else if ($(this).data('action')) {
                    var a = $(this).data('action');
                    map.getEventManager().trigger(a, $this.data("value"));
                }
            });

            $('.vdMapButton', this._ctx.get(0)).mouseup(function(ev) {
                buttonPressed = false;
                var $this = $(this);
                if ($this.data('toggle') == undefined) {
                    $this.removeClass("selected");
                }
                if ($this.data('pressable')) {
                    cancelAnimationFrame(self.interval);
                }
            });

            $("body").on( "mouseup touchend", function(ev) {

                if (buttonPressed) {
                    if (buttonPressed.data("toggle") == undefined) {
                        buttonPressed.removeClass("selected");
                    }
                    cancelAnimationFrame(self.interval);
                }
            });
        }
        this.handleToolbarPlacement();
    };
    ViewerBaseUi.prototype.handleToolbarPlacement = function() {
        var placement = parseInt( this.params.toolbarPlacement );
        /**
         * @type HTMLElement
         */
        var canvasElement = this.canvasElement.get(0);
        /**
         * @type HTMLElement
         */
        var uiButtonBar = this.UiButtonBar.get(0);
        switch( placement ) {
            case ToolbarPlacement.NO_PLACEMENT:
            break;
            case ToolbarPlacement.LEFT_INSIDE:
                alignLeftCenter( canvasElement, uiButtonBar, true );
            break;
            case ToolbarPlacement.LEFT_OUTSIDE:
                alignLeftCenter( canvasElement, uiButtonBar, false );
            break;
            case ToolbarPlacement.RIGHT_INSIDE:
                alignRightCenter( canvasElement, uiButtonBar, true );
            break;
            case ToolbarPlacement.RIGHT_OUTSIDE:
                alignRightCenter( canvasElement, uiButtonBar, false );
            break;
        }
    };
    
    ViewerBaseUi.prototype.floorChanged = function(ev, pFloor) {
        this.floorsMenu.children('li').removeClass('vdActive');
        var floorLi = this.floorsMenu.children('[data-value="' + pFloor + '"]');
        if( floorLi.length ) {
            floorLi.addClass('vdActive');
        } else {
            this._waitingFloor = pFloor;
        }

    };

    ViewerBaseUi.prototype.toString = function() {
        return "[ViewerBaseUi Connected User Interface]";
    }

    ViewerBaseUi.prototype.shapePicked = function(ev, shape) {
        if (!shape || shape.Id != this._currentShape) {
            this._currentDestination = null;
            this.closePanel();
        }
    };

    ViewerBaseUi.prototype.closePanel = function() {
        if (this.loadingInterval != null) {
            clearInterval(this.loadingInterval);
            this.loadingInterval = null;
        }
        if( this.destinationPanel ) {
            this.destinationPanel.removeClass("open").addClass("close").removeClass("loading");
            this.destinationPanel.trigger( "panelClosing", [this]  );
        }
    };

    ViewerBaseUi.prototype.openPanel = function() {
        if( this.destinationPanel ) {
            this.destinationPanel.removeClass("close").addClass("open");
            this.destinationPanel.trigger( "panelOpening", [this]  );
        }
    };

    /**
     * when a destination is picked
     */
    ViewerBaseUi.prototype.destinationPicked = function(ev, infos) {
        if (infos.destinationId != this._currentDestination) {
            this._currentDestination = infos.destinationId;
            if ( this.itinerarySelectorPanel && this.params.allowChangeItineraryDestination ) {
                this.itinerarySelectorPanel.find('select[name="end-destination"]').val(this._currentDestination);
            }
            this._currentShape = infos.shapeId;
            if( this.params.displayInfoPanel ) {
                this.load(this._currentDestination);
            }
        }
    };

    /**
     * when a destination is picked
     */
    ViewerBaseUi.prototype.destinationRollOver = function(ev, infos) {
        if ( this.destinationPanel ) {
            this.load(infos.destinationId);
        }
    };

    /**
     * retrieves panels details from a local cache or remote
     * 
     * @param {Number}
     *                pId identifier
     */
    ViewerBaseUi.prototype.load = function(pId) {
        
    };

    ViewerBaseUi.prototype.destinationRollOut = function(ev, infos) {
        if( this.destinationPanel ) {
            this._expectingId = null;
            if (this._currentDestination !== null ) {
                this.load(this._currentDestination);
                this.openPanel();
            } else {
                this.closePanel();
            }
        }
    };

    ViewerBaseUi.prototype.datasLoaded = function(pId, pData) {
        /*if (pId == this._expectingId) {
            this.loadingFinished(null, null);
            this.display(pData);
        }
        this._cacheStore[pId] = pData;*/
    };

    ViewerBaseUi.prototype.setContent = function(pContent) {
        this.destinationPanel.html("<div class='content-wrapper'>" + pContent + "</div>");
    };

    ViewerBaseUi.prototype.display = function(pContent) {
        clearInterval(this.loadingInterval);
        this.loadingInterval = null;
        if( this.destinationPanel ) {
            this.destinationPanel.removeClass("loading");
            this.setContent(pContent);
            this.openPanel();
        }
    };

    ViewerBaseUi.prototype._buildPathControls = function() {
        try{
            //var c = $('<ul class="path-controls"><li title="'+Drupal.t("see previous part of the path", {}, { context : 'viadirect_map_canvas' })+'" class="prev"><span>&nbsp;</span></li><li class="next"  title="'+Drupal.t("see next part of the path", {}, { context : 'viadirect_map_canvas' })+'"><span>&nbsp;</span></li></ul>');
            var c = $('<ul class="path-controls"><li title="see previous part of the path" class="prev"><span>&nbsp;</span></li><li class="next"  title="see next part of the path"><span>&nbsp;</span></li></ul>');

            c.on( 'click', "li", $.proxy( this.pathControlClicked, this ) );
            return c;
        }catch(error){
            console.log(error.message);
        }
    };
    
    ViewerBaseUi.prototype.pathControlClicked = function( ev ) {
        var $li = $(ev.target);
        if( $li.hasClass('next') ) {
            this._currentPathStep++;
        }
        if( $li.hasClass('prev') ) {
            this._currentPathStep--;
        }
        this.updatePathControls();
    };
    
    ViewerBaseUi.prototype.updatePathControls = function() {
        if( this.pathControls ) {
            if( this._currentPathStep == 0 ) this.pathControls.find('li.prev').hide();
            else this.pathControls.find('li.prev').show();
            
            if( this._currentPathStep == this._currentPath.floors.length-1) this.pathControls.find('li.next').hide();
            else this.pathControls.find('li.next').show();
        }
        this.app.getEventManager().trigger( "vdUIFloorChange", this._currentPath.floors[this._currentPath.floors.length - 1 - this._currentPathStep]);
    }
    
    ViewerBaseUi.prototype.pathReady = function(ev, pathObject) {
        var controls;
        console.log( pathObject );
        this._currentPath = pathObject;
        this._currentPathStep = 0;
        if( pathObject ) {
            if( pathObject && pathObject.floors.length > 1 ) {
                
                controls = this._buildPathControls();
                this.pathControls = controls.appendTo( this.itinerarySelectorPanel );
                
            } else {
                if( this.pathControls )
                this.pathControls.detach();
            }
            this.updatePathControls();
        }
    };
    
    ViewerBaseUi.prototype.resizeUi = function(ev) {
        this.handleToolbarPlacement();
    }
    
    function alignLeftCenter( eleRef, eleTarget, inset ){
        var r1 = eleRef.getBoundingClientRect(), r2 = eleTarget.getBoundingClientRect();
        var oL = r1.left - r2.left;
        var oT = r1.top - r2.top;
        
        var h1 = ( r1.bottom - r1.top ),
            h2 = ( r2.bottom - r2.top ),
            w1= ( r1.right - r1.left ),
            w2 = ( r2.right - r2.left );

            var cT = ( h1 - h2 ) / 2;
        
        oT += cT;
        oL -= (inset ? 0 : w2);

        eleTarget.style.top = (eleTarget.offsetTop + oT) + "px";
        eleTarget.style.left = (eleTarget.offsetLeft + oL) + "px"
    }

    function alignRightCenter( eleRef, eleTarget, inset ) {
        var r1 = eleRef.getBoundingClientRect(), r2 = eleTarget.getBoundingClientRect();
        var oL = r1.left - r2.left;
        var oT = r1.top - r2.top;
        
        var h1 = ( r1.bottom - r1.top ),
            h2 = ( r2.bottom - r2.top ),
            w1= ( r1.right - r1.left ),
            w2 = ( r2.right - r2.left );

        var cT = ( h1 - h2 ) / 2;

        oT += cT;
        oL += w1 + (inset ? -w2 : 0);

        eleTarget.style.top = (eleTarget.offsetTop + oT) + "px";
        eleTarget.style.left = (eleTarget.offsetLeft + oL) + "px";
    }

/**
 * @require ViewerBaseUi.js
 */

OfflineViewerUi = function(){
    ViewerBaseUi.call( this );
};

// @inherit ViewerBaseUi
OfflineViewerUi.prototype = Object.create( ViewerBaseUi.prototype );
OfflineViewerUi.prototype.constructor = ViewerBaseUi;

OfflineViewerUi.prototype.load = function( pId ) {
    
};

/**
 * build and display the details in the panel
 * 
 * @private
 * @param {Destination}
 *                pDestination
 * @param {Number}
 *                languageIndex
 */
OfflineViewerUi.prototype._displayDestination = function(pDestination, languageIndex) {

    if( this.canDisplayPanel) {
        var tpl = "<h1>%TITLE%</h1>%IMG%<h2 class='category'>%CATEGORY%</h2><h2 class='topic'>%TOPIC%</h2><p><span class='description'>%DESCRIPTION%</span></p>";
        var tokensMap = {
            TITLE : pDestination.NameTranslations[languageIndex],
            CATEGORY : pDestination.Categories.length ? pDestination.Categories[0].NameTranslations[languageIndex] : "",
            TOPIC : pDestination.Topics.length ? pDestination.Topics[0].NameTranslations[languageIndex] : "",
            IMG : pDestination.LogoUrl ? "<img src='" + this.pathManager.getPath() + "/"
                    + pDestination.LogoUrl.Translations[languageIndex].LocalUrl + "'/>" : "",
            DESCRIPTION : pDestination.DescriptionTranslations[languageIndex],
        };
        var txt = tpl.replace(/%(\w+)%/g, function(match, token, offset, string) {
            return tokensMap[token]
        });
    
        this.destinationPanel.removeClass("close").addClass("open");
        this.destinationPanel.html(txt);
    }
};

OfflineViewerUi.prototype.toString = function() {
    return "[OfflineViewerUi Connected User Interface]";
}
/**
 * 
 */


    var requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame
            || window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
    var cancelAnimationFrame = window.cancelAnimationFrame || window.mozCancelAnimationFrame;
    
    var ViewerBaseUi = function(settings) {
        this._cacheStore = {};
        this.params = $.extend({}, ViewerBaseUi.prototype.defaultParams, settings);
        this._currentDestination = null;
        this._currentShape = null;
        this.loadingInterval = null;
        this.interval = null;
    };
    
    /**
     * @type {Object}
     */
    var ToolbarPlacement = {
        NO_PLACEMENT: 0,
        LEFT_INSIDE: 1,
        LEFT_OUTSIDE: 2,
        RIGHT_INSIDE: 3,
        RIGHT_OUTSIDE: 4
    }

    ViewerBaseUi.prototype.defaultParams = {
        labelLoading : "loading",
        startingDestinationsCategories: [],
        endingDestinationsCategories: [],
        displayInfoPanel: true,
        displayItineraryPanel: true,
        allowChangeItineraryDestination: true,
        toolbarPlacement: 0,
        displayToolbar: true,
        displayPMR: true
    };

    ViewerBaseUi.prototype._waitingFloor = null;
    /**
     * 
     * @param {VDCanvasViewerBootstrap} app
     * @param dispatcher
     * @param {Object} params
     */
    ViewerBaseUi.prototype.register = function(app, dispatcher, params) {
        /**
         * @type {VDCanvasViewerBootstrap}
         */
        this.app = app;
//        dispatcher.bind('loadingQueueStart', $.proxy( this.loadingStart, this));
//        dispatcher.bind('loadingQueueFinished', $.proxy( this.loadingFinished, this));
        dispatcher.bind('vdInitializeContainer', $.proxy( this.initializeContainer, this));
        dispatcher.bind('vdShapePicked', $.proxy( this.shapePicked, this));
        dispatcher.bind('vdDestinationPicked', $.proxy( this.destinationPicked, this));
        dispatcher.bind('vdDestinationRollOver', $.proxy( this.destinationRollOver, this));
        dispatcher.bind('vdDestinationRollOut', $.proxy( this.destinationRollOut, this));
        dispatcher.bind('vdFloorChanged', $.proxy( this.floorChanged, this));
        dispatcher.bind('vdModelReady', $.proxy( this.modelReady, this));
        dispatcher.bind('vdPathCalculated', $.proxy( this.pathReady, this));
        dispatcher.bind('vdResize', $.proxy( this.resizeUi, this ));
        
        this.params = $.extend( true, {}, this.params, params );
        //if( app.params.selectedDestination ) this._currentDestination = app.params.selectedDestination;
        // handling parameters
        this.pathManager = app.getPathManager();
    };

    /*ViewerBaseUi.prototype.loadingStart = function(ev, settings) {
        if (this.loadingInterval != null)
            return;
        var self = this;
        this.destinationPanel.removeClass('close').addClass("loading");
        this.destinationPanel.trigger( "panelLoading", [this] );
        var num = 0;
        var c = function(time) {
            var label = Drupal.t( 'Loading', {}, {context: "viadirect_map_canvas"}) + " ";
            num = Math.min(++num, 4);
            if (num == 4)
                num = 0;
            for (var i = 0; i < num; i++)
                label = label + ".";
            self.setContent(label);
        }
        c(0);
        this.loadingInterval = setInterval(c, 200);

    };

    ViewerBaseUi.prototype.loadingFinished = function(ev, settings) {
        clearInterval(this.loadingInterval);
        this.loadingInterval = null;
        if( this.destinationPanel ) {
            this.destinationPanel.removeClass("loading");
        }
    };*/

    /**
     * initialize the container
     * 
     * @param Event
     *                ev
     * @param JQueryResultSet
     *                pContext
     */
    ViewerBaseUi.prototype.initializeContainer = function(ev, pContext) {
        this._ctx = $("<div class='vdUserInterface'></div>").appendTo(pContext);
        this.UiButtonBar = $('<div class="vdUiButtons"></div>');
        this.floorsMenu = $('<div class="vdChangeFloorButtons loading"></div>').appendTo( this.UiButtonBar );
        if( this.params.displayInfoPanel ) {
            this.destinationPanel = $("<div class='destinationInfoArea close'><div class='content-wrapper'></div></div>").appendTo(this._ctx);
        }
        this.zoomControls = $(
                '<ul class="vdZoomButtons"><li class="vdMapButton vdMapZoom" data-action="vdUIZoomIn" data-pressable="1" title="Zoom in"><span>+</span></li><li class="vdMapButton vdMapZoom"  data-pressable="1" data-action="vdUIZoomOut" title="Zoom out"><span>-</span></li> '+ ( this.params.displayItineraryPanel ? '<li class="vdMapButton showItinerary" data-toggle="1" title="Show itinerary">&nbsp;</li>' : '' ) + '</ul>').appendTo( this.UiButtonBar );
        this.loaderLayer = $('<div class="vdLoader"></div>').appendTo(pContext);

        if( this.params.displayToolbar ) {
            this.UiButtonBar.appendTo(this._ctx);
        }
        
        $('.vdMapButton.showItinerary', this.zoomControls.get(0)).click($.proxy(this.showItineraryPanel, this));
        
        this.canvasElement = pContext.find(' > canvas.vdMap ');
    };

    ViewerBaseUi.prototype._filterDestinationHasShape = function(ele) {
        return "ShapeList" in ele && ele.ShapeList.length;
    };
    
    /**
     * 
     * @param ev
     */
    ViewerBaseUi.prototype._buildItineraryPanel = function() {
        var i, len, category, m, key;
        var startingDestinations = [];
        m = this.model.ViadirectMap;

        //Variables para redimensional el panel de itinerario
        var anchoCustom = $(window).width()/3;
        var altoCustom = $(window).height()/3;
        var stringAnchoCustom = anchoCustom+"px";
        var stringAltoCustom = altoCustom+"px";
        var stringMarginTopCustom = "-"+altoCustom/2+"px";
        
        if( this.params.startingDestinationsCategories.length ) {
            for ( key in this.params.startingDestinationsCategories ) {
                if( this.params.startingDestinationsCategories[key] != 0 ) {
                    category = m.FindCategoryByName( key );
                    if ( category )
                        startingDestinations = startingDestinations.concat( startingDestinations, m.FindDestinationsByCategory( category.Name ) );
                }
            }
        } else {
            startingDestinations = this.model.ViadirectMap.DestinationList;
        }
        startingDestinations = startingDestinations.filter(function(ele){ return ele;});
        startingDestinations = startingDestinations.filter( this._filterDestinationHasShape );
        startingDestinations.sort( function(a,b){ return a.NameTranslations[0] > b.NameTranslations[0] ? 1 : -1 });

        var endingDestinations = [];
        var categories = [ 'stores', 'confort' ];
        if( this.params.endingDestinationsCategories.length ) {
            for ( key in this.params.endingDestinationsCategories) {
                if( this.params.endingDestinationsCategories[key] != 0 ) {
                    category = m.FindCategoryByName( this.params.endingDestinationsCategories[key] );
                    if( category ) {
                        endingDestinations = endingDestinations.concat( m.FindDestinationsByCategory( category.Name ) );
                    }
                }
            }
        } else {
            endingDestinations = this.model.ViadirectMap.DestinationList;
        }
        
        endingDestinations = endingDestinations.filter( this._filterDestinationHasShape );
        endingDestinations = $.unique( endingDestinations );
        endingDestinations.sort( function(a,b){ return a.NameTranslations[0] > b.NameTranslations[0] ? 1 : -1 });
        
        var startOptStr = "<option>Choose your starting point</option>";
        for (i = 0, len = startingDestinations.length; i < len; i++) {
            startOptStr += "<option value='" + startingDestinations[i].Id + "'>" + startingDestinations[i].NameTranslations[0] + "</option>";
        }
        var endDestinationStr = "";
        if( this.params.allowChangeItineraryDestination) {
            var endOptStr = "<option>Choose your destination</option>";
            for (i = 0, len = endingDestinations.length; i < len; i++) {
                endOptStr += "<option value='" + endingDestinations[i].Id + "'>" + endingDestinations[i].NameTranslations[0] + "</option>";
            }
            endDestinationStr = '<select class="value" name="end-destination">' + endOptStr + '</select>';
        } else {
            endDestinationStr = '<span class="value">' + this.model.ViadirectMap.FindDestinationById( this._currentDestination ).NameTranslations[0] +'</span>';
        }
        var tpl = '<div class="itineraryPanel" style="width:'+stringAnchoCustom+';height:'+stringAltoCustom+'; position:absolute; margin:auto; top:50%; margin-top:'+stringMarginTopCustom+'; right:0; bottom:0; left:0;"><a href="#" class="close-button">x</a><h1>Choose your itinerary</h1><div><div><span class="label">Start :</span><select class="value" name="start-destination">' + startOptStr + '</select></div> <div><span class="label">Arrival :</span>' + endDestinationStr +' </div>';
        
        if( this.params.displayPMR && this.model.ViadirectMap.FloorList.length > 1) {
            // pmr 
            tpl += '<div class="pmr-row"><input type="checkbox" name="pmr" title="Enable PRM mode" />Enable PRM mode</div>';
        }
        
        //btn btn mostrar, para hacer la búsqueda solo apretando el boton y no siempre que se elija el selector
        tpl+='<div class="btn-cerrar-div"><input type="button" value="Mostrar" id="btn-mostrar-itinerario" name="btn-mostrar-itinerario" /></div>';

        tpl += '</div></div>';
        this.itinerarySelectorPanel = $(tpl);

        //this.itinerarySelectorPanel.on('change', 'select', $.proxy(this.itinerarySelectChange, this));
        this.itinerarySelectorPanel.on('click', 'a.close-button', $.proxy(this.closeItineraryPanel, this));
        //this.itinerarySelectorPanel.on('change', ':input[name=pmr]', $.proxy( this.requestPmr, this));

        //Aplico estas acciones al botón
        this.itinerarySelectorPanel.on('click', ':input[name=btn-mostrar-itinerario]',$.proxy(this.itinerarySelectChange, this));
        this.itinerarySelectorPanel.on('click', ':input[name=btn-mostrar-itinerario]',$.proxy( this.requestPmr, this));
        //Cierra el panel tambien
        this.itinerarySelectorPanel.on('click', ':input[name=btn-mostrar-itinerario]',$.proxy(this.closeItineraryPanel, this));
    };

    ViewerBaseUi.prototype.requestPmr = function(ev) {
        console.log( ev.target.checked );
        this.app.getEventManager().trigger( "vdSetPMRMode", ev.target.checked );
    };
    
    ViewerBaseUi.prototype.closeItineraryPanel = function(ev) {
        this.itinerarySelectorPanel.detach();
        $('.vdMapButton.showItinerary').removeClass("selected").data('toggle', 1);
        if (ev) {
            ev.stopPropagation();
        }
        return false;
    };

    ViewerBaseUi.prototype.itinerarySelectChange = function(ev) {

        var startDestinationId = this.itinerarySelectorPanel.find('select[name="start-destination"] option:selected').val();
        var endDestinationId = this.params.allowChangeItineraryDestination ? this.itinerarySelectorPanel.find('select[name="end-destination"] option:selected').val() : this._currentDestination;

        // sending to app
        this.model.getEventManager().trigger('vdRequestItinerary', [ parseInt(startDestinationId), parseInt(endDestinationId) ]);
    };

    ViewerBaseUi.prototype.showItineraryPanel = function(ev) {
        if ($(ev.target).data('toggle') == 1) {
            if (!this.itinerarySelectorPanel)
                this._buildItineraryPanel();

            //Se añade al body para poder centrar y con position absolute
            //this.itinerarySelectorPanel.appendTo(this._ctx);
            this.itinerarySelectorPanel.appendTo($(document.body));

            if (this._currentDestination) {
                this.itinerarySelectorPanel.find('select[name="end-destination"]').val(this._currentDestination);
            }
            $(ev.target).data('toggle', 0);
        } else {

            this.closeItineraryPanel(null);
            $(ev.target).data('toggle', 1);
        }
    };

    /**
     * 
     * @param {Event}
     *                ev
     * @param {MapManipulator}
     *                map
     */
    ViewerBaseUi.prototype.modelReady = function(ev, map) {

        this.model = map;
        var floorsMenu = $('<ul class="vdChangeFloorButtons"></ul>');
        var floorList = map.ViadirectMap.FloorList;
        for ( var key in floorList) {
            $(
                    '<li class="vdMapButton vdMapFloor '+ (this._waitingFloor == floorList[key].Name ? "vdActive" : "") + '" data-action="vdUIFloorChange" data-value="' + floorList[key].Name
                            + '" ><span>' + floorList[key].NameTranslations[0] + '</span></li>').appendTo(floorsMenu);
        }

        if (floorsMenu.children().length > 1) {
            floorsMenu.children('li').click($.proxy(this.floorButtonPicked, this));
            $('.vdChangeFloorButtons', this._ctx.get(0)).replaceWith(floorsMenu);
            this.floorsMenu = floorsMenu;
        } else {
            $('.vdChangeFloorButtons', this._ctx.get(0)).detach();
        }
        var self = this;
        var o = null;
        var callback = function(target, map) {
            return function(t) {
                if (o === null)
                    o = t;
                var delta = (t - o);

                var a = $(target).data('action');
                if ((delta == 0 || delta > 100) && a) {
                    map.getEventManager().trigger(a);
                    o = t;
                }
                self.interval = requestAnimationFrame(arguments.callee);
            };
        };
        if (requestAnimationFrame && cancelAnimationFrame) {
            var buttonPressed = false;

            $('.vdMapButton', this._ctx.get(0)).on( "mousedown touchstart", function(ev) {
                console.log(this);
                var $this = $(this);
                $this.addClass("selected");
                if ($this.data('pressable')) {
                    buttonPressed = $this;
                    cancelAnimationFrame(self.interval);
                    o = null;

                    self.interval = requestAnimationFrame(callback(this, map));
                } else if ($(this).data('action')) {
                    var a = $(this).data('action');
                    map.getEventManager().trigger(a, $this.data("value"));
                }
            });

            $('.vdMapButton', this._ctx.get(0)).mouseup(function(ev) {
                buttonPressed = false;
                var $this = $(this);
                if ($this.data('toggle') == undefined) {
                    $this.removeClass("selected");
                }
                if ($this.data('pressable')) {
                    cancelAnimationFrame(self.interval);
                }
            });

            $("body").on( "mouseup touchend", function(ev) {

                if (buttonPressed) {
                    if (buttonPressed.data("toggle") == undefined) {
                        buttonPressed.removeClass("selected");
                    }
                    cancelAnimationFrame(self.interval);
                }
            });
        }
        this.handleToolbarPlacement();
    };
    ViewerBaseUi.prototype.handleToolbarPlacement = function() {
        var placement = parseInt( this.params.toolbarPlacement );
        /**
         * @type HTMLElement
         */
        var canvasElement = this.canvasElement.get(0);
        /**
         * @type HTMLElement
         */
        var uiButtonBar = this.UiButtonBar.get(0);
        switch( placement ) {
            case ToolbarPlacement.NO_PLACEMENT:
            break;
            case ToolbarPlacement.LEFT_INSIDE:
                alignLeftCenter( canvasElement, uiButtonBar, true );
            break;
            case ToolbarPlacement.LEFT_OUTSIDE:
                alignLeftCenter( canvasElement, uiButtonBar, false );
            break;
            case ToolbarPlacement.RIGHT_INSIDE:
                alignRightCenter( canvasElement, uiButtonBar, true );
            break;
            case ToolbarPlacement.RIGHT_OUTSIDE:
                alignRightCenter( canvasElement, uiButtonBar, false );
            break;
        }
    };
    
    ViewerBaseUi.prototype.floorChanged = function(ev, pFloor) {
        this.floorsMenu.children('li').removeClass('vdActive');
        var floorLi = this.floorsMenu.children('[data-value="' + pFloor + '"]');
        if( floorLi.length ) {
            floorLi.addClass('vdActive');
        } else {
            this._waitingFloor = pFloor;
        }

    };

    ViewerBaseUi.prototype.toString = function() {
        return "[ViewerBaseUi Connected User Interface]";
    }

    ViewerBaseUi.prototype.shapePicked = function(ev, shape) {
        if (!shape || shape.Id != this._currentShape) {
            this._currentDestination = null;
            this.closePanel();
        }
    };

    ViewerBaseUi.prototype.closePanel = function() {
        if (this.loadingInterval != null) {
            clearInterval(this.loadingInterval);
            this.loadingInterval = null;
        }
        if( this.destinationPanel ) {
            this.destinationPanel.removeClass("open").addClass("close").removeClass("loading");
            this.destinationPanel.trigger( "panelClosing", [this]  );
        }
    };

    ViewerBaseUi.prototype.openPanel = function() {
        if( this.destinationPanel ) {
            this.destinationPanel.removeClass("close").addClass("open");
            this.destinationPanel.trigger( "panelOpening", [this]  );
        }
    };

    /**
     * when a destination is picked
     */
    ViewerBaseUi.prototype.destinationPicked = function(ev, infos) {
        if (infos.destinationId != this._currentDestination) {
            this._currentDestination = infos.destinationId;
            if ( this.itinerarySelectorPanel && this.params.allowChangeItineraryDestination ) {
                this.itinerarySelectorPanel.find('select[name="end-destination"]').val(this._currentDestination);
            }
            this._currentShape = infos.shapeId;
            if( this.params.displayInfoPanel ) {
                this.load(this._currentDestination);
            }
        }
    };

    /**
     * when a destination is picked
     */
    ViewerBaseUi.prototype.destinationRollOver = function(ev, infos) {
        if ( this.destinationPanel ) {
            this.load(infos.destinationId);
        }
    };

    /**
     * retrieves panels details from a local cache or remote
     * 
     * @param {Number}
     *                pId identifier
     */
    ViewerBaseUi.prototype.load = function(pId) {
        
    };

    ViewerBaseUi.prototype.destinationRollOut = function(ev, infos) {
        if( this.destinationPanel ) {
            this._expectingId = null;
            if (this._currentDestination !== null ) {
                this.load(this._currentDestination);
                this.openPanel();
            } else {
                this.closePanel();
            }
        }
    };

    ViewerBaseUi.prototype.datasLoaded = function(pId, pData) {
        /*if (pId == this._expectingId) {
            this.loadingFinished(null, null);
            this.display(pData);
        }
        this._cacheStore[pId] = pData;*/
    };

    ViewerBaseUi.prototype.setContent = function(pContent) {
        this.destinationPanel.html("<div class='content-wrapper'>" + pContent + "</div>");
    };

    ViewerBaseUi.prototype.display = function(pContent) {
        clearInterval(this.loadingInterval);
        this.loadingInterval = null;
        if( this.destinationPanel ) {
            this.destinationPanel.removeClass("loading");
            this.setContent(pContent);
            this.openPanel();
        }
    };

    ViewerBaseUi.prototype._buildPathControls = function() {
        var c = $('<ul class="path-controls"><li title="'+Drupal.t("see previous part of the path", {}, { context : 'viadirect_map_canvas' })+'" class="prev"><span>&nbsp;</span></li><li class="next"  title="'+Drupal.t("see next part of the path", {}, { context : 'viadirect_map_canvas' })+'"><span>&nbsp;</span></li></ul>');
        c.on( 'click', "li", $.proxy( this.pathControlClicked, this ) );
        return c;
    };
    
    ViewerBaseUi.prototype.pathControlClicked = function( ev ) {
        var $li = $(ev.target);
        if( $li.hasClass('next') ) {
            this._currentPathStep++;
        }
        if( $li.hasClass('prev') ) {
            this._currentPathStep--;
        }
        this.updatePathControls();
    };
    
    ViewerBaseUi.prototype.updatePathControls = function() {
        if( this.pathControls ) {
            if( this._currentPathStep == 0 ) this.pathControls.find('li.prev').hide();
            else this.pathControls.find('li.prev').show();
            
            if( this._currentPathStep == this._currentPath.floors.length-1) this.pathControls.find('li.next').hide();
            else this.pathControls.find('li.next').show();
        }
        this.app.getEventManager().trigger( "vdUIFloorChange", this._currentPath.floors[this._currentPath.floors.length - 1 - this._currentPathStep]);
    }
    
    ViewerBaseUi.prototype.pathReady = function(ev, pathObject) {
        var controls;
        console.log( pathObject );
        this._currentPath = pathObject;
        this._currentPathStep = 0;
        if( pathObject ) {
            if( pathObject && pathObject.floors.length > 1 ) {
                
                controls = this._buildPathControls();
                this.pathControls = controls.appendTo( this.itinerarySelectorPanel );
                
            } else {
                if( this.pathControls )
                this.pathControls.detach();
            }
            this.updatePathControls();
        }
    };
    
    ViewerBaseUi.prototype.resizeUi = function(ev) {
        this.handleToolbarPlacement();
    }
    
    function alignLeftCenter( eleRef, eleTarget, inset ){
        var r1 = eleRef.getBoundingClientRect(), r2 = eleTarget.getBoundingClientRect();
        var oL = r1.left - r2.left;
        var oT = r1.top - r2.top;
        
        var h1 = ( r1.bottom - r1.top ),
            h2 = ( r2.bottom - r2.top ),
            w1= ( r1.right - r1.left ),
            w2 = ( r2.right - r2.left );

            var cT = ( h1 - h2 ) / 2;
        
        oT += cT;
        oL -= (inset ? 0 : w2);

        eleTarget.style.top = (eleTarget.offsetTop + oT) + "px";
        eleTarget.style.left = (eleTarget.offsetLeft + oL) + "px"
    }

    function alignRightCenter( eleRef, eleTarget, inset ) {
        var r1 = eleRef.getBoundingClientRect(), r2 = eleTarget.getBoundingClientRect();
        var oL = r1.left - r2.left;
        var oT = r1.top - r2.top;
        
        var h1 = ( r1.bottom - r1.top ),
            h2 = ( r2.bottom - r2.top ),
            w1= ( r1.right - r1.left ),
            w2 = ( r2.right - r2.left );

        var cT = ( h1 - h2 ) / 2;

        oT += cT;
        oL += w1 + (inset ? -w2 : 0);

        eleTarget.style.top = (eleTarget.offsetTop + oT) + "px";
        eleTarget.style.left = (eleTarget.offsetLeft + oL) + "px";
    }


})( jQuery, window );
