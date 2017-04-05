(function($, global) {

    /**
     *
     */


    /**
     * @constructor
     * @memberof viadirect.canvas.viewer
     * @param {jQuery} jquery resultset
     * @param {pPrefix}
     */
    function EventManagerDecorator(pTarget, pPrefix) {
        this._target = pTarget;
        this._prefix = pPrefix ? pPrefix : "";
    };

    /**
     * the jquery event dispatcher
     * @type {Object}
     */
    EventManagerDecorator.prototype._target = null;

    /**
     * @type {String}
     */
    EventManagerDecorator.prototype._prefix = "";

    /**
     *
     * @param pName
     * @returns
     */
    EventManagerDecorator.prototype.trigger = function(pName) {
        var args = Array.prototype.slice.call(arguments);
        args[0] += ("." + this._prefix);
        //    console.log( "trigger " + args );
        this._target.trigger.apply(this._target, args);
        return this;
    };

    /**
     *
     * @returns
     */
    EventManagerDecorator.prototype.bind = function() {
        var args = Array.prototype.slice.call(arguments);
        args[0] += ("." + this._prefix);
        //    console.log( "bind " + args );
        this._target.bind.apply(this._target, args);
        return this;
    };

    /**
     *
     * @returns
     */
    EventManagerDecorator.prototype.unbind = function() {
        var args = Array.prototype.slice.call(arguments);
        args[0] += ("." + this._prefix);
        this._target.unbind.apply(this._target, args);
        return this;
    };

    /**
     *
     * @param {String} pPrefix
     */
    EventManagerDecorator.prototype.setPrefix = function(pPrefix) {
        this._prefix = pPrefix !== undefined ? pPrefix : "";
        return this;
    };

    /**
     *
     *
     * @file PathManager.js
     *
     */

    /**
     * @constructor
     * @this {PathManager}
     * @param {Object}
     *                parameters object
     */
    function PathManager(params) {
        this.setParams(params);
    };

    /**
     * @var {String}
     */
    PathManager.prototype.protocol = "";

    /**
     * @var {String}
     */
    PathManager.prototype.hostName = "";

    /**
     * @var {String}
     */
    PathManager.prototype.path = "";

    /**
     *
     * @param {Object}
     *                pParams
     */
    PathManager.prototype.setParams = function(pParams) {
        $.extend(this, pParams);
        console.log(this.protocol);
    };

    /**
     *
     * @returns {String}
     */
    PathManager.prototype.getPath = function() {
        return this.protocol + "://" + this.hostName + this.path;
    };

    /**
     *
     * @param params
     * @returns
     */
    PathManager.prototype.getBuildingUrl = function() {
        return [this.getPath(), this.buildingFilename].join('/');
    };

    /**
     *
     * @param pName
     * @returns {String}
     */
    PathManager.prototype.getFloorUrl = function(pName) {
        return [this.getPath(), this.floorPath, pName, pName + ".json"].join('/');
    };

    /**
     *
     * @param pName
     * @returns {String}
     */
    PathManager.prototype.getCategoryUrl = function(pName) {
        return [this.getPath(), this.categoryPath, pName, pName + ".json"].join('/');
    };

    /**
     *
     * @param pName
     * @returns {String}
     */
    PathManager.prototype.getTopicUrl = function(pName) {
        return [this.getPath(), this.topicsPath, pName, pName + ".json"].join('/');
    };


    /**
     *
     * @param pName
     * @returns {String}
     */
    PathManager.prototype.getHiddenTopicUrl = function(pName, folder) {
        if (!folder)
            return [this.getPath(), this.hiddenTopicsPath, pName, pName + ".json"].join('/');
        else
            return [this.getPath(), this.hiddenTopicsPath, pName].join('/');
    };

    /**
     *
     * @param pName
     * @returns {String}
     */
    PathManager.prototype.getFloorTextureUrl = function(pName, floorTexturePath) {
        if (!floorTexturePath)
            return [this.getPath(), this.floorPath, pName, this.floorTextureName].join('/');

        return [this.getPath(), floorTexturePath].join('/');
    };

    /**
     *
     * @param pName
     * @returns {String}
     */
    PathManager.prototype.getDestinationUrl = function(pName, onlyFolder) {
        if (onlyFolder) {
            return [this.getPath(), this.destinationPath, pName].join('/');
        }
        return [this.getPath(), this.destinationPath, pName, pName + ".json"].join('/');
    };

    PathManager.prototype.getDestinationViewUrl = function(pName, onlyFolder) {
        return this.getDestinationUrl(pName, onlyFolder);
    };

    PathManager.prototype.getArrowImageUrl = function() {
        return "./resources/pictos/arrow.svg";
    };

    PathManager.prototype.getElevatorUpImageUrl = function() {
        return "./resources/pictos/ascenseur-up.svg";
    };

    PathManager.prototype.getElevatorDownImageUrl = function() {
        return "./resources/pictos/escalator-down.svg";
    };

    PathManager.prototype.getEscalatorUpImageUrl = function() {
        return "./resources/pictos/escalator-up.svg";
    };

    PathManager.prototype.getEscalatorDownImageUrl = function() {
        return "./resources/pictos/escalator-down.svg";
    };

    PathManager.prototype.getStairsUpImageUrl = function() {
        return "./resources/pictos/escalier-up.svg";
    };

    PathManager.prototype.getStairsDownImageUrl = function() {
        return "./resources/pictos/escalier-down.svg";
    };
    /**
     *
     * @file Color.js
     * @package viadirect.model
     */

    /**
     * Stores a color in the rgba forma
     * @class Color
     * @constructor
     * @param {number}  r - Red Componant
     * @param {number}  g - Green Componant
     * @param {number}  b - Blue Componant
     * @param {?number} a - (optional) Alpha Componant, 255 by default
     */
    function Color(r, g, b, a) {
        this.R = r;
        this.G = g;
        this.B = b;

        if (a !== null)
            this.A = a;
    };

    Color.prototype = {

        /**
         * Red
         * @type {number}
         */
        R: null,

        /**
         * Green
         * @type {number}
         */
        G: null,

        /**
         * Blue
         * @type {number}
         */
        B: null,

        /**
         * Alpha - Default : 255
         * @type {number}
         */
        A: 255,

        toSimpleObject: function() {

            return {
                R: this.R,
                G: this.G,
                B: this.B,
                A: this.A

            };
        }
    };
    /**
     *
     * @require viadirect/model/Color.js
     */

    /*
     * @class CanvasHelper
     * @namespace utils
     */
    CanvasHelper = {};

    /**
     * @desc Converts a color from a packed int XNA color to a Color Object
     * @param {number} packedColor - The color packed in the c# XNA standard
     * @returns {Color}
     */
    CanvasHelper.convertPackedColorToColor = function(packedColor) {
        var b = (packedColor >> 0) & 255;
        var g = (packedColor >> 8) & 255;
        var r = (packedColor >> 16) & 255;
        var a = (packedColor >> 24) & 255;
        var color = new Color(r, g, b, a);
        //
        return color;
    };

    /**
     * @desc Convert a point coordintates from the XNA reference to the canvas one
     * @param {Point}  oldPoint          - Point XNA coordinates
     * @param {Point}  jsonMapUpperPoint - Upper point in XNA reference
     * @param {Point}  jsonMapLowerPoint - Lower point in XNA reference
     * @param {number} canvasHeight      - Height of the Canvas
     * @param {number} canvasWidth       - Width of the Canvas
     * @returns {Point}
     */
    CanvasHelper.DimensionsConverter = function(oldPoint, jsonMapUpperPoint, jsonMapLowerPoint, canvasHeight,
        canvasWidth) {
        var mapWidth = Math.abs(jsonMapUpperPoint.X - jsonMapLowerPoint.X);
        var mapHeight = Math.abs(jsonMapUpperPoint.Y - jsonMapLowerPoint.Y);

        var tempX = oldPoint.X - jsonMapLowerPoint.X;
        var tempY = oldPoint.Y - jsonMapLowerPoint.Y;
        tempX = (tempX * canvasWidth) / mapWidth;
        tempY = (tempY * canvasHeight) / mapHeight;

        var tempZ = oldPoint.Z;

        if (tempZ)
            tempZ = tempZ * 1000;

        return new Point(tempX, tempY, tempZ);
    };

    /**
     * @desc Convert a point coordintates from the canvas reference to the XNA one
     * @param {Point}  oldPoint          - Point XNA coordinates
     * @param {Point}  jsonMapUpperPoint - Upper point in XNA reference
     * @param {Point}  jsonMapLowerPoint - Lower point in XNA reference
     * @param {number} canvasHeight      - Height of the Canvas
     * @param {number} canvasWidth       - Width of the Canvas
     * @returns {Point}
     */
    CanvasHelper.DimensionsReverter = function(oldPoint, jsonMapUpperPoint, jsonMapLowerPoint, canvasHeight,
        canvasWidth) {
        var mapWidth = Math.abs(jsonMapUpperPoint.X - jsonMapLowerPoint.X);
        var mapHeight = Math.abs(jsonMapUpperPoint.Y - jsonMapLowerPoint.Y);

        var tempX = (oldPoint.X * mapWidth) / canvasWidth;
        var tempY = (oldPoint.Y * mapHeight) / canvasHeight;
        tempX = tempX - Math.abs(jsonMapLowerPoint.X);
        tempY = tempY - Math.abs(jsonMapLowerPoint.Y);

        return new Point(tempX, tempY);
    };

    /**
     * @desc Returns the angle Point1 Ô Point2 in radians
     * @param {Point} Point1
     * @param {Point} Point2
     * @returns {number}
     */
    CanvasHelper.GetAngle = function(Point1, Point2) {
        var newPoint = new Point();

        newPoint.X = Point2.X - Point1.X;
        newPoint.Y = Point2.Y - Point1.Y;

        var angle = -Math.atan2(newPoint.X, newPoint.Y) + Math.PI / 2;
        return angle;
    };

    /**
     * @desc Applies a rotation on a point with the origin as a center
     * @param {Point}  point - Point to transform
     * @param {number} teta  - Rotation angle ( radians )
     * @returns {Point}
     */
    CanvasHelper.rotationMatrice = function(point, teta) {
        var rotatedPoint = new Point();

        if (point.Z) {
            alert("3D");
        } else {
            rotatedPoint.X = point.X * Math.cos(teta) - point.Y * Math.sin(teta);
            rotatedPoint.Y = point.X * Math.sin(teta) + point.Y * Math.cos(teta);
        }

        return rotatedPoint;
    };

    /**
     * @desc Applies a translation and a rotation to a point
     * @param {Point}  point       - Point to transform
     * @param {Point}  translation - Translation Vector ( from 0;0 to translation.X;translation.Y )
     * @param {number} teta        - Rotation angle ( radians )
     * @returns {Point}
     */
    CanvasHelper.changementRepere = function(point, translation, teta) {
        var finalPoint = new Point();

        finalPoint.X = point.X - translation.X;
        finalPoint.Y = point.Y - translation.Y;

        if (teta)
            finalPoint = CanvasHelper.rotationMatrice(finalPoint, teta);

        return finalPoint;
    };

    /**
     * @desc Returns the length of a line
     * @param {Point} Point1 - First point of the line
     * @param {Point} Point2 - Second point of the line
     * @returns {number}
     */
    CanvasHelper.GetLength = function(Point1, Point2) {
        var length = (Point1.X - Point2.X) * (Point1.X - Point2.X) + (Point1.Y - Point2.Y) * (Point1.Y - Point2.Y);
        return Math.sqrt(length);
    };

    /*
     * @class JsonNavigator
     * @namespace utils
     */
    JsonNavigator = {};

    /**
     * @desc
     * @param {object} pTarget - Element in which we will navigate.
     * @param {string] pPath   - Path to follow inside the pTarget elements. Splits on /
     * @returns
     */
    JsonNavigator.walkPath = function(pTarget, pPath) {

        var node = pTarget;
        var path = pPath.split('/');
        try {
            for (var i = 0, len = path.length; i < len; i++) {
                node = node[path[i]];
            }
            return node;
        } catch (err) {
            console.log("throw in JsonNavigator.walkPath : " + err.message);
            return null;
        }
    };
    /*
     * @class PathCalculator
     * @namespace utils
     */
    PathCalculator = {};

    PathCalculator.CalculatePath = function(pListOfPath, pStartingNode, pEndingNode, isPMR) {
        var closeNodes = new Array();
        var openNodes = new Array();
        var cameFrom = new Array();
        openNodes.push({
            node: pStartingNode,
            gscore: 0,
            fscore: 0 + PathCalculator.HeuristicCostEstimate(pStartingNode, pEndingNode)
        });

        while (openNodes.length != 0) {
            var current = PathCalculator.FindLowerFScoreValue(openNodes);

            if (current.node == pEndingNode)
                return PathCalculator.ReconstructPath(cameFrom, pEndingNode);

            openNodes = PathCalculator.RemoveNodeFromArray(openNodes, current);
            closeNodes.push(current);

            var relatedPoints = PathCalculator.FindPointsRelatedToPoint(pListOfPath, current.node, isPMR);

            for (var i = 0; i < relatedPoints.length; i++) {
                if (PathCalculator.TabContainsNode(closeNodes, relatedPoints[i]))
                    continue;

                var tempGScore = current.gscore + PathCalculator.HeuristicCostEstimate(current.node, relatedPoints[i]);
                var item = PathCalculator.FindNodeInArray(openNodes, relatedPoints[i]);

                if (!item ||
                    tempGScore < item.gscore) {

                    var tempFScore = tempGScore + PathCalculator.HeuristicCostEstimate(relatedPoints[i], pEndingNode);
                    cameFrom[relatedPoints[i].Id] = current.node;

                    if (!item) {
                        openNodes.push({
                            node: relatedPoints[i],
                            gscore: tempGScore,
                            fscore: tempFScore
                        });
                    } else {
                        item.gscore = tempGScore;
                        item.gscore = tempFScore;
                    }
                }
            }
        }

        console.log("pas de chemin");
        return null;
    };

    PathCalculator.FindNodeInArray = function(tab, node) {
        for (key in tab) {
            if (tab[key].node == node)
                return tab[key];
        }

        return null;
    };

    PathCalculator.TabContainsNode = function(tab, node) {
        if (PathCalculator.FindNodeInArray(tab, node))
            return true;
        return false;
    };

    PathCalculator.RemoveNodeFromArray = function(tab, item) {
        var index = tab.indexOf(item);

        if (index > -1) {
            tab.splice(index, 1);
        }

        return tab;
    };

    PathCalculator.FindLowerFScoreValue = function(tab) {
        if (tab.length == 0)
            return null;

        var lowest;

        for (var i = 0; i < tab.length; i++) {
            if (!lowest)
                lowest = tab[i];

            if (lowest.fscore > tab[i].fscore) {
                lowest = tab[i];
            }
        }

        return lowest;
    };

    PathCalculator.HeuristicCostEstimate = function(pNode1, pNode2) {
        var Point1 = pNode1.Position;
        var Point2 = pNode2.Position;

        return Math.sqrt((Point1.X - Point2.X) * (Point1.X - Point2.X) + (Point1.Y - Point2.Y) * (Point1.Y - Point2.Y) + (Point1.Z - Point2.Z) * (Point1.Z - Point2.Z));
    };

    PathCalculator.ReconstructPath = function(cameFromTab, current) {
        var totalPath = new Array();
        totalPath.push(current);
        var length = 0;
        var floors = new Array();

        do {
            var previous = current;
            current = cameFromTab[current.Id];

            if (current) {
                totalPath.push(current);
                length += PathCalculator.HeuristicCostEstimate(previous, current);

                if (floors.indexOf(current.Floor.Name) == -1)
                    floors.push(current.Floor.Name);
            }
        }
        while (PathCalculator.ReconstructPathChecker(cameFromTab, current));

        return {
            path: PathCalculator.pathCleaner(totalPath),
            length: length,
            floors: floors,
            toSimpleObject: function() {
                return {
                    length: this.length,
                    floors: this.floors,
                    path: this.path.toSimpleObject()
                }
            }
        };
    };


    PathCalculator.pathCleaner = function(path) {
        var hasModif;

        do {
            hasModif = false;

            for (var i = 0; i < path.length - 2; i++) {
                if (!(path[i].Floor.Name == path[i + 1].Floor.Name && path[i + 1].Floor.Name == path[i + 2].Floor.Name))
                    continue;

                if (path[i].Position.X == path[i + 1].Position.X && path[i + 1].Position.X == path[i + 2].Position.X) {
                    PathCalculator.RemoveNodeFromArray(path, path[i + 1]);
                    hasModif = true;
                }

                if (path[i].Position.Y == path[i + 1].Position.Y && path[i + 1].Position.Y == path[i + 2].Position.Y) {
                    PathCalculator.RemoveNodeFromArray(path, path[i + 1]);
                    hasModif = true;
                }
            }
        } while (hasModif);

        path.toSimpleObject = this.toSimpleObject;
        return path;
    };

    PathCalculator.toSimpleObject = function() {
        var o = [];
        for (var i = 0, len = this.length; i < len; i++) {
            o.push(this[i].toSimpleObject());
        }
        return o;
    };

    PathCalculator.ReconstructPathChecker = function(tab, node) {
        for (key in tab) {
            if (tab[key] == node)
                return tab[key];
        }

        return null;
    };

    PathCalculator.FindPointsRelatedToPoint = function(pListOfPath, pNode, isPMR) {

            var resultPoints = new Array();

            for (var i = 0; i < pListOfPath.length; i++) {
                if (isPMR && !pListOfPath[i].AccesibleToDisabledPeople)
                    continue;

                if (!isPMR && pListOfPath[i].IsPMROnly)
                    continue;

                if (pListOfPath[i].StartingPoint == pNode) {
                    resultPoints.push(pListOfPath[i].EndingPoint);
                }
                if (pListOfPath[i].EndingPoint == pNode) {
                    resultPoints.push(pListOfPath[i].StartingPoint);
                }
            }

            return resultPoints;
        }
        // All urls and paths to navigate in the map

    /*
     * Text Ressources related constants
     */

    var TEXT_RESSOURCES_PATH_TO_NAME_TRANSLATION_TEXT = "Text";

    var TEXT_RESSOURCES_PATH_TO_NAME_TRANSLATION_ABBREVIATION = "Abbreviation";

    /*
     * Destination related constants
     */

    var DESTINATION_FROM_ROOT_PATH_TO_ID = "Id";
    var DESTINATION_FROM_ROOT_PATH_TO_TRANSLATIONS = "TextResource/Translations";
    var DESTINATION_FROM_ROOT_PATH_TO_NAME = "TextResource/Name";
    var DESTINATION_FROM_ROOT_PATH_TO_DESCRIPTION = "Description/Translations";
    var DESTINATION_FROM_ROOT_PATH_TO_HIDDEN_TOPICS = "HiddenTopics";
    var DESTINATION_FROM_ROOT_PATH_TO_TOPICS = "Topics";
    var DESTINATION_FROM_ROOT_PATH_TO_CATEGORIES = "Categories";
    var DESTINATION_FROM_ROOT_PATH_TO_ACTUAL_SHAPE_LIST = "ShapeIndexes/ActualShapeList";


    var DESTINATION_PATH_URL = "Destinations/";

    var DESTINATION_LOGO_URL_PREFIX = "MapLogo_";

    var DESTINATION_PATH_TO_ROOT = "Value";

    var DESTINATION_PATH_TO_NAME = "Value/TextResource/Name";

    var DESTINATION_PATH_TO_TRANSLATIONS = "Value/TextResource/Translations";

    var DESTINATION_PATH_TO_DESCRIPTION = "Value/Description/Translations";

    var DESTINATION_PATH_TO_ID = "Value/Id";

    var DESTINATION_PATH_TO_HIDDEN_TOPICS = "Value/HiddenTopics";

    var DESTINATION_PATH_TO_TOPICS = "Value/Topics";

    var DESTINATION_PATH_TO_CATEGORIES = "Value/Categories";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS = "ExternalDatas";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA = "Data";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_KEY = "key";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_VALUE_UND = "und";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_VALUE_EN = "en";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_VALUE_RU = "ru";

    var DESTINATION_PATH_TO_ACTUAL_SHAPE_LIST = "Value/ShapeIndexes/ActualShapeList";

    var DESTINATION_PATH_TO_ACTUAL_SHAPE_LIST_FLOOR_ID = "FloorId";

    var DESTINATION_PATH_TO_ACTUAL_SHAPE_LIST_ID = "Id";

    /*
     * Category related constants
     */

    var CATEGORY_PATH_URL = "Categories/";

    var CATEGORY_PATH_TO_NAME = "TextResource/Name";

    var CATEGORY_PATH_TO_ID = "Id";

    var CATEGORY_PATH_TO_TEXT_RESSOURCES = "TextResource/Translations";

    /*
     * Path points related constants
     */

    var PATHPOINTS_PATH_POSITION = "Position";

    var PATHPOINTS_PATH_FLOOR_REFERENCE = "Floor";

    var PATHPOINTS_PATH_DESTINATION_REFERENCE = "DestinationReference";

    var PATHPOINTS_PATH_ID = "Id";

    var PATHPOINTS_PATH_FLOOR_POSITION = "Floor";

    var PATHPOINTS_PATH_NAME = "Name";

    /*
     * Path related constants
     */

    var PATH_PATH_STARTING_POINT = "StartPointId";

    var PATH_PATH_ENDING_POINT = "FinishPointId";

    var PATH_PATH_ACESSIBLE_DISABLE = "AccesibleToDisabledPeople";

    var PATH_PATH_ID = "Id";

    var PATH_PATH_DIRECTION = "PathDirection";

    var PATH_PATH_FROM_DAY = "UsableFromDay";

    var PATH_PATH_FROM_HOUR = "UsableFromHour";

    var PATH_PATH_TILL_DAY = "UsableTillDay";

    var PATH_PATH_TILL_HOUR = "UsableTillHour";

    /*
     * Topic related constants
     */

    var TOPIC_PATH_URL = "Topics/";

    var TOPIC_PATH_TO_NAME = "TextResource/Name";

    var TOPIC_PATH_TO_ID = "Id";

    var TOPIC_PATH_TO_TEXT_RESSOURCES = "TextResource/Translations";

    var TOPIC_PATH_TO_COLOR = "Color";

    var TOPIC_PATH_TO_PACKED_COLOR = "Color/packedValue";

    /*
     * Hidden Topic related constants
     */

    var HIDDEN_TOPIC_PATH_URL = "HiddenTopics/";

    /*
     * Floors related constants
     */

    // Floors folder
    var FLOORS_PATH_URL = "Floors/";

    // Texture Path Url
    var FLOOR_TEXTURE_PATH_URL = "Texture.png";

    // Lower point label in Json
    var FLOOR_LOWER_POINT_LABEL = "LowerPoint";

    // Lower point path from floor
    var FLOOR_LOWER_POINT_PATH = "LowerPoint";

    // Position path from floor
    var FLOOR_POSITION_PATH = "Position";

    var FLOOR_TEXTURE_HEIGHT = "Height";

    var FLOOR_TEXTURE_WIDTH = "Width";

    // Upper point label in Json
    var FLOOR_UPPER_POINT_LABEL = "UpperPoint";

    // Upper point path from floor
    var FLOOR_UPPER_POINT_PATH = "UpperPoint";

    // Path to Floor Name From Floor
    var FLOOR_PATH_TO_NAME = "TextResource/Name";

    var FLOOR_PATH_TO_TEXTURE_NAME = "Texture";

    var FLOOR_PATH_TO_PACKED_COLOR = "Color/packedValue";

    var FLOOR_PATH_TO_NAME_TRANSLATIONS = "TextResource/Translations";

    // Path to Shapes Array from Floor
    var FLOOR_PATH_TO_SHAPES = "Shapes";

    // Path to Shapes points from Shape
    var FLOOR_PATH_TO_SHAPE_POINTS = "Value/ShapePoints";
    var FLOOR_PATH_TO_SHAPE_ID = "Value/Id";
    var FLOOR_PATH_TO_SHAPE_COLOR_PACKED_VALUE = "Value/Color/packedValue";
    var FLOOR_PATH_TO_SHAPE_SELECTABLE = "Value/Selectable";
    var FLOOR_PATH_TO_SHAPE_HEIGHT = "Value/Height";
    var FLOOR_PATH_TITLE_BASELINE = "Value/TitleBaseline/PointList";
    var FLOOR_PATH_CENTER_POINT = "Value/Center";
    var FLOOR_PATH_TITLE_BASELINE_MAXHEIGHT = "Value/TitleBaseline/MaxTextHeight";

    /*
     * Building related constants
     */

    // Building.json Name
    var BUILDING_JSON = "building.json";

    // Floors label in Json
    var BUILDING_FLOORS_LABEL = "Floors";

    // Path to floors in building.json
    var BUILDING_FLOORS_PATH = "Floors";

    var BUILDING_PATH_TO_DESTINATIONS = "Destinations";

    var BUILDING_PATH_TO_CATEGORIES = "CategoriesId";

    var BUILDING_PATH_TO_TOPICS = "TopicsId";

    var BUILDING_PATH_TO_HIDDEN_TOPICS = "HiddenTopicsId";

    /*
     * Canvas related Constants
     */

    var CANVAS_DRAWABLE_BACKGROUND_TEXTURE = "BackgroundTexture";

    var CANVAS_DRAWABLE_VIADIRECT_SHAPE = "ViaDirectShape";

    var CANVAS_DRAWABLE_VIADIRECT_PATHPOINT = "ViaDirectPathPoint";

    var CANVAS_DRAWABLE_VIADIRECT_PATH = "ViaDirectPath";

    var CANVAS_DRAWABLE_VIADIRECT_CIRCLE = "ViaDirectCircle";

    var CANVAS_DRAWABLE_PICTO = "ViaDirectPicto";

    var CANVAS_DRAWABLE_BILBOARD = "ViaDirectBillboard";

    var CANVAS_DRAWABLE_LINE = "Line";

    var CANVAS_LABEL_BLOC = "BlockLabel";

    /*
     * Url Parameters Constants
     */

    var URL_SELECT_DESTINATION = "destination";

    var URL_SELECT_FLOOR = "floor";

    var URL_UI_STATUS = "ui";

    var CONSTANT_DOORS_CATEGORY = "doors";
    /**
     *
     * @file CssProperties.js
     * @package viadirect.model
     */

    /**
     * Stores Css Properties for elements
     * @class CssProperties
     */
    function CssProperties() {
        this.otherProperties = new Array();
    };

    CssProperties.prototype = {
        fontSize: 0,

        fontFamily: null,

        backgroundColor: "",

        color: "",

        textTransform: "",

        paddingLeft: 0,

        paddingRight: 0,

        paddingTop: 0,

        paddingBottom: 0,

        borderWidth: 0,

        borderColor: "",

        display: "",

        maxFontSize: "",

        maxHeight: "",

        otherProperties: null,

        toSimpleObject: function() {
            return {
                fontSize: this.fontSize,
                fontFaimly: this.fontFamily,
                backgroundColor: this.backgroundColor,
                color: this.color,
                textTransform: this.textTransform,
                paddingLeft: this.paddingLeft,
                paddingRight: this.paddingRight,
                paddingTop: this.paddingTop,
                paddingBottom: this.paddingBottom,
                borderWidth: this.borderWidth,
                borderColor: this.borderColor,
                display: this.display,
                maxFontSize: this.maxFontSize,
                maxHeight: this.maxHeight,
                otherProperties: this.otherProperties
            };
        }
    };
    /**
     *
     * @file Point.js
     * @package viadirect.model
     */

    /**
     * @class Point
     * @constructor
     * @param   {?number} newX - The X coordonate of the point
     * @param   {?number} newY - The Y coordonate of the point
     * @param   {?number} newZ - The Z coordonate of the point
     */
    function Point(newX, newY, newZ) {
        this.X = newX;
        this.Y = newY;
        this.Z = newZ;
    };

    Point.prototype = {

        /**
         * @type {?number}
         */
        X: null,

        /**
         * @type {?number}
         */
        Y: null,

        /**
         * @type {?number}
         */
        Z: null,

        toSimpleObject: function() {
            return {
                X: this.X,
                Y: this.Y,
                Z: this.Z
            };
        }
    };
    /**
     * @file BlocLabel.js
     * @package viadirect.model
     * @require viadirect/model/CssProperties.js
     * @require viadirect/model/Point.js
     */

    /**
     * Specific structure used to store a text and the line on which it will be drawn
     * @class BlocLabel
     * @constructor
     * @param {Point[]} pointArray - The line on which the text will be drawn
     * @param {string}  text       - The text to be written
     */
    function BlocLabel(line, text) {
        this.PointList = new Array();

        if (line)
            this.PointList = line;

        this.Label = text;

        this.StartingPoint = new Point();

        this.CssPropertiesList = new CssProperties();
    }

    BlocLabel.prototype = {

        /**
         * The line on which the text will be drawn. Contains 2 Point objects.
         * The order matters as the text will be written from the first Point to the second one.
         * @type {Point[]}
         */
        PointList: new Array(),

        StartingPoint: new Point(),
        /**
         * association with a Shape
         * @type {Shape}
         */
        Owner: null,
        /**
         * @type {Number}
         */
        Angle: 0,

        /**
         * The label to be drawn on the canvas.
         * @type {string}
         */
        Label: "",

        /**
         * The maximum height the baseline text object can take
         * @type Number
         */
        MaxTextHeight: null,

        CssPropertiesList: null,

        toSimpleObject: function() {
            var i = 0,
                len = this.PointList.length;
            var pointlist = new Array(len);
            for (; i < len; i++) pointlist[i] = this.PointList[i].toSimpleObject();
            return {
                PointList: pointlist,
                StartingPoint: this.StartingPoint.toSimpleObject(),
                Owner: null,
                Label: this.Label,
                Angle: this.Angle
            };
        }
    };

    BlocLabel.prototype.hasPoints = function() {
        return this.PointList.length > 0;
    };

    /**
     *
     * @file Shape.js
     * @package viadirect.model
     * @require viadirect/model/BlocLabel.js
     */

    /**
     * A shape in the Viadirect map
     *
     * @class Shape
     * @constructor
     */
    function Shape() {
        this.PointList = [];
        this.DestinationsList = [];
        /**
         * @type {BlocLabel}
         */
        this.TitleBaseline = new BlocLabel();
        this.TitleBaseline.Owner = this;
        this.CenterPoint = null;
    }

    Shape.prototype = {

        /**
         * List of the Point objects composing the shape
         *
         * @type Point[]
         */
        PointList: [],

        /**
         * @type Destinations[]
         */
        DestinationsList: [],
        /**
         * The label associated with the shape
         *
         * @type {BlocLabel}
         */
        TitleBaseline: new BlocLabel(),

        /**
         * The color of the shape
         *
         * @type Color|string
         */
        Color: null,

        /**
         * The stroke color of the shape
         *
         * @type Color|string
         */
        StrokeColor: 'black',

        /**
         * The stroke width of the shape (in pixels)
         *
         * @type Number
         */
        StrokeWidth: 1,

        /**
         * Unique shape Id
         *
         * @type Number
         */
        Id: null,

        /**
         * Tells if the shape is made to be selected via mousepicking
         *
         * @type Boolean
         */
        Selectable: false,

        /**
         * Center point of the Shape
         *
         * @type ?Point
         */
        CenterPoint: new Point(),

        /**
         * Name of the floor containing this shape
         *
         * @type String
         */
        FloorName: "",

        GetBounds: function() {
            var i, len, minX = Number.NEGATIVE_INFINITY,
                minY = Number.NEGATIVE_INFINITY,
                maxX = Number.POSITIVE_INFINITY,
                maxY = Number.POSITIVE_INFINITY;
            for (i = 0, len = this.PointList.length; i < len; i++) {
                minX = Math.min(this.PointList[i].X, minX);
                minY = Math.min(this.PointList[i].Y, minY);
                maxY = Math.max(this.PointList[i].Y, maxY);
                maxX = Math.max(this.PointList[i].X, maxX);
            }
            return {
                left: minX,
                right: maxX,
                top: minY,
                bottom: maxY,
                leftTop: new Point(minX, minY),
                rightBottom: new Point(maxX, maxY),
                width: (maxX - minX),
                height: (maxY - minY)
            }
        },

        /**
         * Clones the current Shape
         *
         * @returns Shape
         */
        Clone: function() {
            var copy = new Shape();

            for (var attr in this) {
                if (this.hasOwnProperty(attr))
                    copy[attr] = this[attr];
            }

            return copy;
        },

        toSimpleObject: function() {
            var i, len;
            var points = new Array(this.PointList.length);

            for (i = 0, len = this.PointList.length; i < len; i++)
                if (this.PointList[i]) points[i] = this.PointList[i].toSimpleObject();
            return {
                CenterPoint: this.CenterPoint.toSimpleObject(),
                Color: (this.Color && (this.Color.constructor !== String && this.Color.constructor !== Number) && "toSimpleObject" in this.Color) ? this.Color.toSimpleObject() : this.Color,
                DestinationsList: this.DestinationsList,
                FloorName: this.FloorName,
                Id: this.Id,
                PointList: points,
                Selectable: this.Selectable,
                StrokeColor: this.StrokeColor, //.toSimpleObject(),
                StrokeWidth: this.StrokeWidth,
                TitleBaseline: this.TitleBaseline.toSimpleObject()
            };
        }
    };
    /**
     *
     * @file Floor.js
     * @package viadirect.model
     * @require viadirect/constants.js
     * @require viadirect/model/Shape.js
     * @require viadirect/model/Point.js
     */

    /**
     * A floor of the Viadirect Map
     * @class Floor
     * @constructor
     */
    function Floor() {
        this.ShapeList = new Array();
        this.NameTranslations = new Array();
        this.Color = null;
        this.UpperBound = new Point();
        this.LowerBound = new Point();
        this.Position = new Point();
        this.Texture = null;
    }

    Floor.prototype = {

        /**
         * List of the shapes on this floor (not only the destinations' shapes, but all of them).
         * @type {Shape[]}
         */
        ShapeList: new Array(),

        /**
         * Unique Name of the floor
         * @type {string}
         */
        Name: "",

        /**
         * All the translations of the floor's name.
         * @type {string[]}
         */
        NameTranslations: new Array(),

        /**
         * Basic color associated with the floor (if any).
         * @type {Color}
         */
        Color: null,

        /**
         * Upper Bound of the map in OLD coordinates system (Only use it to change coordinates).
         * @type {Point}
         */
        UpperBound: new Point(),

        /**
         * Lower Bound of the map in OLD coordinates system (Only use it to change coordinates).
         * @type {Point}
         */
        LowerBound: new Point(),

        /**
         * Background Texture of the floor (if any).
         * @type {?image}
         */
        Texture: new Image(),

        TextureHeight: 0,

        TextureWidth: 0,

        /**
         * 3D Position of the floor
         * @type {Point}
         */
        Position: new Point(),

        /**
         * Adds a Shape object into the floor's shape list
         * @param {Shape} pShape
         */
        AddShape: function(pShape) {
            this.ShapeList.push(pShape);
        },
        toSimpleObject: function() {
            var i = 0,
                len = this.ShapeList.length,
                shapeList = new Array(len);
            for (; i < len; i++)
                if (this.ShapeList[i]) shapeList[i] = this.ShapeList[i].Id;
            return {
                Name: this.Name,
                NameTranslations: this.NameTranslations,
                Color: this.Color.toSimpleObject(),
                ShapeList: shapeList,
                TextureHeight: this.TextureHeight,
                TextureWidth: this.TextureWidth,
                Position: this.Position.toSimpleObject(),
                LowerBound: this.LowerBound.toSimpleObject(),
                UpperBound: this.UpperBound.toSimpleObject()
            };
        }
    };
    /**
     *
     * @file Topic.js
     * @package viadirect.model
     */

    /**
     * A topic of the Viadirect Map
     * @class Topic
     * @constructor
     */
    function Topic() {
        this.NameTranslations = new Array();
        this.Color = null;
    }

    Topic.prototype = {

        /**
         * Unique id of the Topic
         * @type {number}
         */
        Id: null,

        /**
         * Unique name of the Topic
         * @type {string}
         */
        Name: "",

        /**
         * Names' translations of the topic
         * @type {string[]}
         */
        NameTranslations: new Array(),

        /**
         * The color of the topic ( if any ). Null otherwise
         * @type {Color}
         */
        Color: null,

        /**
         * @description      Get the name of the topic for the correspondant language id
         * @param   {number} languageId - The language id to get the trad
         * @returns {string}
         */
        GetNameTranslation: function(languageId) {
            if (!this.NameTranslations)
                return null;

            if (!this.NameTranslations[languageId])
                return null;

            return this.NameTranslations[languageId];
        },

        toSimpleObject: function() {
            return {
                Color: this.Color ? this.Color.toSimpleObject() : null,
                Id: this.Id,
                Name: this.Name,
                NameTranslations: this.NameTranslations
            };
        }
    };

    /**
     *
     * @file Category.js
     * @package viadirect.model
     */

    /**
     * A category of the Viadirect Map
     *
     * @class Category
     * @constructor
     */
    function Category() {
        this.NameTranslations = [];
        this.DestinationsList = [];
    };

    Category.prototype = {

        /**
         * Unique id of the category
         *
         * @type {number}
         */
        Id: null,

        /**
         * Unique name of the category
         *
         * @type {string}
         */
        Name: "",

        /**
         * Names' translations of the category
         *
         * @type {string[]}
         */
        NameTranslations: new Array(),

        /**
         *
         * @type {Array}
         */
        DestinationsList: [],
        /**
         * @description Get the name of the category for the correspondant language
         *              id
         * @param {number}
         *                languageId - The language id to get the trad
         * @returns {string}
         */
        GetNameTranslation: function(languageId) {
            if (!this.NameTranslations)
                return null;

            if (!this.NameTranslations[languageId])
                return null;

            return this.NameTranslations[languageId];
        },

        toSimpleObject: function() {

            var o = {
                Id: this.Id,
                Name: this.Name,
                NameTranslations: this.Name,
                DestinationsList: []
            };
            return o;
        }
    };
    /**
     *
     * @file Destination.js
     * @package viadirect.model
     * @require viadirect/model/Topic.js
     * @require viadirect/model/Category.js
     * @require viadirect/model/Shape.js
     *
     */

    /**
     * A destination of the Viadirect Map
     * @class Destination
     * @constructor
     */
    function Destination() {
        this.NameTranslations = new Array();
        this.DescriptionTranslations = new Array();
        this.HiddenTopics = new Array();
        this.Topics = new Array();
        this.Categories = new Array();
        this.ShapeList = new Array();
        this.PathPoints = [];
        this.AdditionalInfos = null;
    }

    Destination.prototype = {

        /**
         * @type {Array}
         */
        PathPoints: [],
        /**
         * @type {String}
         */
        hiddenPrefix: "x_",
        /**
         * @type {Boolean}
         */
        IsHidden: false,
        /**
         * Unique name of the destination
         * @type {string}
         */
        Name: "",

        /**
         * Names' translations of the destination
         * @type {string[]}
         */
        NameTranslations: new Array(),

        /**
         * Descriptions' translations of the destination
         * @type {string[]}
         */
        DescriptionTranslations: new Array(),

        /**
         * Unique id of the destination
         * @type {number}
         */
        Id: null,

        /**
         * Hidden topics of the destination
         * @type {Topic[]}
         */
        HiddenTopics: new Array(),

        /**
         * Topics of the destination
         * @type {Topic[]}
         */
        Topics: new Array(),

        /**
         * Names' translations of the destination
         * @type {Category[]}
         */
        Categories: new Array(),

        /**
         * Shapes of the destination
         * @type {Shape[]}
         */
        ShapeList: new Array(),

        AdditionalInfos: null,

        /**
         * Names' translations of the category
         * @type {string[]}
         */
        LogoUrl: "",

        HasCategory: function(categoryName) {
            for (category in this.Categories) {
                if (category.Name == categoryName)
                    return true;
            }

            return false;
        },
        HasCategory: function(pName) {
            var i, len, found = false;
            for (i = 0, len = this.Categories.length; i < len; i++) {
                found = this.Categories[i].Name == pName;
                if (found) return true;
            }
            return false;
        },
        /**
         * @description      Get the external ID of the destination, return the viadirect ID by default
         * @param   {number} externalIdKey - the key of th external ID
         * @returns {number}
         */
        GetExternalId: function(externalIdKey) {

            if (!this.AdditionalInfos)
                return this.Id;

            if ((externalIdKey in this.AdditionalInfos) && this.AdditionalInfos[externalIdKey].length && this.AdditionalInfos[externalIdKey][0])
                return this.AdditionalInfos[externalIdKey][0].value;

            return this.Id;
        },

        /**
         * @description      Get the name of the destination for the correspondant language id
         * @param   {number} languageId - The language id to get the trad
         * @returns {string}
         */
        GetNameTranslation: function(languageId) {
            if (!this.NameTranslations)
                return "";

            if (!this.NameTranslations[languageId])
                return "";

            return this.NameTranslations[languageId];
        },

        /**
         * @description        Get the name of the destination's categories for the correspondant language id
         * @param   {number}   languageId - The language id to get the trad
         * @returns {string[]}
         */
        GetCategoriesTranslations: function(languageId) {
            var retval = new Array();

            for (var i = 0, len = this.Topics.length; i < len; i++) {
                retval.push(this.Topics[i].GetNameTranslation(languageId));
            }

            return retval;
        },

        /**
         * @description        Get the name of the destination's topics for the correspondant language id
         * @param   {number}   languageId - The language id to get the trad
         * @returns {string[]}
         */
        GetTopicsTranslations: function(languageId) {
            var retval = new Array();

            for (var i = 0, len = this.Categories.length; i < len; i++) {
                retval.push(this.Categories[i].GetNameTranslation(languageId));
            }

            return retval;
        },

        /**
         * @description        Get the name of the destination's hidden topics for the correspondant language id
         * @param   {number}   languageId - The language id to get the trad
         * @returns {string[]}
         */
        GetHiddenTopicsTranslations: function(languageId) {
            var retval = new Array();

            for (var i = 0, len = this.HiddenTopics.length; i < len; i++) {
                retval.push(this.HiddenTopics[i].GetNameTranslation(languageId));
            }

            return retval;
        },

        /**
         * @description      Get the destination's description for the correspondant language id
         * @param   {number} languageId - The language id to get the trad
         * @returns {string}
         */
        GetDescriptionTranslation: function(languageId) {
            if (!this.DescriptionTranslations)
                return "";

            if (!this.DescriptionTranslations[languageId])
                return "";

            return this.DescriptionTranslations[languageId];
        },

        toSimpleObject: function() {
            var i, len, pathpoints = [],
                shapeList = [],
                categories = [],
                topics = [],
                hiddentopics = [];
            for (i = 0, len = this.Categories.length; i < len; i++) categories[i] = this.Categories[i].Id;
            for (i = 0, len = this.Topics.length; i < len; i++) topics[i] = this.Topics[i].Id;
            for (i = 0, len = this.HiddenTopics.length; i < len; i++) hiddentopics[i] = this.HiddenTopics[i].Id;
            for (i = 0, len = this.PathPoints.length; i < len; i++) pathpoints[i] = this.PathPoints[i].Id;
            for (i = 0, len = this.ShapeList.length; i < len; i++) shapeList[i] = this.ShapeList[i].Id;

            return {
                AdditionalInfos: this.AdditionalInfos,
                Id: this.Id,
                Name: this.Name,
                NameTranslations: this.NameTranslations,
                IsHidden: this.IsHidden,
                hiddenPrefix: this.hiddenPrefix,
                LogoUrl: this.LogoUrl,
                DescriptionTranslations: this.DescriptionTranslations,
                PathPoints: pathpoints,
                ShapeList: shapeList,
                Categories: categories,
                Topics: topics,
                HiddenTopics: hiddentopics
            };
        }
    };
    /**
     *
     * @file PathPoint.js
     * @package viadirect.model
     */

    /**
     * @class PathPoint
     * @constructor
     */
    function PathPoint() {
        this.Position = new Point();
        this.DestinationReference = new Array();
    };

    PathPoint.prototype = {
        Position: new Point(),
        /**
         * @type {Number[]}
         */
        DestinationReference: [],

        /**
         * @type {String}
         */
        Name: "",

        /**
         * @type {Number}
         */
        Id: 0,
        /**
         * @type {Floor}
         */
        Floor: null,

        toSimpleObject: function() {
            return {
                Position: this.Position.toSimpleObject(),
                Name: this.name,
                Id: this.Id,
                Floor: this.Floor.toSimpleObject()
            };
        }
    };
    /**
     *
     * @file Path.js
     * @package viadirect.model
     */

    /**
     * @class Path
     * @constructor
     */
    function Path() {
        this.StartingPoint = new Point();
        this.EndingPoint = new Point();
    };

    Path.prototype = {
        /**
         * @type {Point}
         */
        StartingPoint: new Point(),

        /**
         * @type {Point}
         */
        EndingPoint: new Point(),

        /**
         * @type {boolean}
         */
        AccesibleToDisabledPeople: true,

        /**
         * @type {boolean}
         */
        IsPMROnly: false,

        /**
         * @type {Number}
         */
        Id: -1,

        /**
         * @type {Number}
         */
        UsableFromDay: null,

        /**
         * @type {Number}
         */
        UsableFromHour: null,

        /**
         * @type {Number}
         */
        UsableTillDay: null,

        /**
         * @type {Number}
         */
        UsableTillHour: null,

        /**
         * @type {Number}
         */
        PathDirection: 0,

        toSimpleObject: function() {
            return {
                StartingPoint: this.StartingPoint.toSimpleObject(),
                EndingPoint: this.EndingPoint.toSimpleObject(),
                AccesibleToDisabledPeople: this.AccesibleToDisabledPeople,
                IsPMROnly: this.IsPMROnly,
                Id: this.Id,
                UsableFromDay: this.UsableFromDay,
                UsableFromHours: this.UsableFromHour,
                UsableTillDay: this.UsableTillDay,
                UsableTillHours: this.UsableTillHours,
                PathDirection: 0
            }

        }
    };
    /**
     *
     * @file ResourcesHolder.js
     */

    /**
     * @class ResourcesHolder
     * @constructor
     * @namespace viadirect.canvas.viewer
     */
    function ResourcesHolder(pObj) {
        for (var key in pObj) {
            if (key in ResourcesHolder.prototype) {
                this[key] = pObj[key];
            }
        }
        if (!("__proto__" in this) && ("getPrototypeOf" in Object)) this.__proto__ = Object.getPrototypeOf(this);
        else if (!"__proto__" in this) this.__proto__ = this.constructor.prototype;
    };

    ResourcesHolder.prototype = {
        hasResource: function(pName) {
            return pName in this.__proto__;
        },
        ArrowIcon: null,

        EscalatorUp: null,

        EscalatorDown: null,

        ElevatorUp: null,

        ElevatorDown: null,

        StairsUp: null,

        StairsDown: null,

        YouAreHere: null,

        ArrowPattern: null
    };
    /**
     *
     * @file ViadirectMap.js
     * @package viadirect.model
     * @require viadirect/model/Floor.js
     * @require viadirect/model/Destination.js
     * @require viadirect/model/Category.js
     * @require viadirect/model/Topic.js
     * @require viadirect/model/PathPoint.js
     * @require viadirect/model/Path.js
     * @require viadirect/model/ResourcesHolder.js
     */

    /**
     * @class ViadirectMap
     * @constructor
     * @namespace viadirect.canvas.viewer
     */
    function ViadirectMap() {
        this.FloorList = new Array();
        this.DestinationList = new Array();
        this.CategoriesList = new Array();
        this.TopicList = new Array();
        this.HiddenTopicList = new Array();
        this.ShapeList = new Array();
        this.PathPointsList = new Array();
        this.PathList = new Array();
        this.ResourcesMap = new ResourcesHolder();
    };

    ViadirectMap.prototype = {

        /**
         * List of all the floors
         *
         * @type {Floor[]}
         */
        FloorList: new Array(),

        /**
         * List of all the Destinations
         *
         * @type {Destination[]}
         */
        DestinationList: new Array(),

        /**
         * List of all the Categories
         *
         * @type {Category[]}
         */
        CategoriesList: new Array(),

        /**
         * List of all the Topics
         *
         * @type {Topic[]}
         */
        TopicList: new Array(),

        /**
         * List of all the Hidden Topics
         *
         * @type {Topic[]}
         */
        HiddenTopicList: new Array(),

        /**
         * List of all the Shapes
         *
         * @type {Shape[]}
         */
        ShapeList: new Array(),

        /**
         *
         * @type {Object}
         */
        ResourcesMap: {},

        /**
         * Returns the path points associated to a floor Name
         *
         * @param {string}
         *                floorName - the name of the floor
         * @returns {PathPoints[]} The PathPoints associated to the floor Name
         */
        FindPathPointsByFloor: function(floorName) {
            var pathPointsArray = new Array();

            for (var key in this.PathPointsList) {
                if (this.PathPointsList[key].Floor.Name == floorName) {
                    pathPointsArray.push(this.PathPointsList[key]);
                }
            }
            return pathPointsArray;
        },

        FindAllPathByFloor: function(floorName) {
            var floorPaths = new Array();
            var pointsArray = this.FindPathPointsByFloor(floorName);

            for (var i = 0; i < pointsArray.length; i++) {
                for (var j = 0; j < this.PathList.length; j++) {
                    if (pointsArray[i] == this.PathList[j].StartingPoint) {
                        for (var k = 0; k < pointsArray.length; k++) {
                            if (pointsArray[k] == this.PathList[j].EndingPoint) {
                                floorPaths.push(this.PathList[j]);
                                break;
                            }
                        }
                    }
                }
            }

            return floorPaths;
        },
        /**
         * get a list of path points associated to a destination
         * @param destinationID
         * @returns {Array}
         */
        FindPointsAssociatedToDestinationID: function(destinationID) {
            var pointsList = new Array();

            for (ppkey in this.PathPointsList) {
                var current = this.PathPointsList[ppkey];

                for (dkey in current.DestinationReference) {
                    if (current.DestinationReference[dkey] == destinationID)
                        pointsList.push(current);
                }
            }

            return pointsList;
        },

        FindPathPointById: function(pathPointId) {
            for (var key in this.PathPointsList) {
                if (this.PathPointsList[key].Id == pathPointId) {
                    return this.PathPointsList[key];
                }
            }

            return null;
        },

        /**
         * Returns the destinations associated to a PathPoint
         *
         * @param {PathPoint}
         *                pathPoint - the pathPoint
         * @returns {Destination[]} The Destinations associated to the PathPoint
         */
        FindDestinationAssociatedToPathPoint: function(pathPoint) {
            var destinationsArray = new Array();

            for (var i = 0; i < pathPoint.DestinationReference.length; i++) {

                for (var key in this.DestinationList) {
                    if (this.DestinationList[key].Id == pathPoint.DestinationReference[i]) {
                        destinationsArray.push(this.DestinationList[key]);
                    }
                }
            }

            return destinationsArray;
        },

        /**
         * Returns the floor with the given floor name
         *
         * @param {string}
         *                floorName - the name of the floor
         * @returns {Floor} the floor with the given floor name
         */
        FindFloorByName: function(floorName) {
            for (var key in this.FloorList) {
                if (this.FloorList[key].Name == floorName) {
                    return this.FloorList[key];
                }
            }
            return null;
        },

        /**
         * Add a floor to the map
         *
         * @param {Floor}
         *                pFloor
         */
        AddFloor: function(pFloor) {
            this.FloorList.push(pFloor);
        },

        /**
         * find a category by its name identifier
         * @param {String} pName
         * @returns {Category}
         */
        FindCategoryByName: function(pName) {
            var i, len;
            for (i = 0, len = this.CategoriesList.length; i < len; i++) {
                if (this.CategoriesList[i].Name == pName) {
                    return this.CategoriesList[i];
                }
            }

        },

        /**
         * return all destinations identified by this category
         * @param pCategory
         * @returns {Array} a list of destination identified by this category
         */
        FindDestinationsByCategory: function(pCategory) {
            var c = this.FindCategoryByName(pCategory);
            if (c)
                return c.DestinationsList;
            return [];

        },

        /**
         * Returns the destination with the given destination id
         *
         * @param destinationId
         * @returns {Destination}
         */
        FindDestinationById: function(destinationId) {
            for (var key in this.DestinationList) {
                if (this.DestinationList[key].Id == destinationId) {
                    return this.DestinationList[key];
                }
            }
        },

        toSimpleObject: function() {
            var i, len, categories = new Array(this.CategoriesList.length),
                destinations = new Array(this.DestinationList.length),
                floors = new Array(this.FloorList.length),
                hiddentopics = new Array(this.HiddenTopicList.length),
                topics = new Array(this.TopicList.length),
                shapes = new Array(this.ShapeList.length),
                paths = new Array(this.PathList.length),
                pathpoints = new Array(this.PathPointsList.length);

            for (i = 0, len = this.CategoriesList.length; i < len; i++)
                if (this.CategoriesList[i]) categories[i] = this.CategoriesList[i].toSimpleObject();
            for (i = 0, len = this.DestinationList.length; i < len; i++)
                if (this.DestinationList[i]) destinations[i] = this.DestinationList[i].toSimpleObject();
            for (i = 0, len = this.FloorList.length; i < len; i++)
                if (this.FloorList[i]) floors[i] = this.FloorList[i].toSimpleObject();
            for (i = 0, len = this.HiddenTopicList.length; i < len; i++)
                if (this.HiddenTopicList[i]) hiddentopics[i] = this.HiddenTopicList[i].toSimpleObject();
            for (i = 0, len = this.TopicList.length; i < len; i++)
                if (this.TopicList[i]) topics[i] = this.TopicList[i].toSimpleObject();
            for (i = 0, len = this.PathList.length; i < len; i++)
                if (this.PathList[i]) paths[i] = this.PathList[i].toSimpleObject();
            for (i = 0, len = this.PathPointsList.length; i < len; i++)
                if (this.PathPointsList[i]) pathpoints[i] = this.PathPointsList[i].toSimpleObject();
            for (i = 0, len = this.ShapeList.length; i < len; i++)
                if (this.ShapeList[i]) shapes[i] = this.ShapeList[i].toSimpleObject();

            return {
                CategoriesList: categories,
                DestinationList: destinations,
                FloorList: floors,
                HiddenTopicList: hiddentopics,
                ShapeList: shapes,
                TopicList: topics,
                PathList: paths,
                PathPointsList: pathpoints
            };
        }
    };

    /**
     *
     * @file Scene.js
     * @package viadrect.model
     */

    /**
     * Object that holds all the elements to be drawn on the canvas.
     * @class Scene
     * @constructor
     */
    function Scene() {
        this.ItemsList = new Array();
    }

    Scene.prototype = {

        /**
         * Holds all the elements of the current canvas scene
         * @type {DrawableItem[]}
         */
        ItemsList: new Array(),

        /**
         * Add an element to the current scene object. Doesn't trigger a canvas draw.
         * @param {DrawableItem} item - Item to add to the scene
         */
        AddItem: function(item) {
            this.ItemsList.push(item);
        }
    };
    /**
     *
     * @file DrawableItem.js
     * @package viadirect.model
     */

    /**
     * A drawable item. Usually stored into the Scene object.
     * It can hold every drawable item and an id, alowing it to be drawn.
     * @class DrawableItem
     * @constructor
     * @param {Object} item
     * @param {String} type
     */
    function DrawableItem(item, type) {
        this.ItemType = type;
        this.Item = item;
    }

    DrawableItem.prototype = {

            /**
             * Holds a constant defining the type of the DrawableItem.
             * All the possible values are listed in the constant.js file.
             * Theses values are ( as listed in the rev:18 ) :
             * - CANVAS_DRAWABLE_BACKGROUND_TEXTURE : "BackgroundTexture"
             * - CANVAS_DRAWABLE_VIADIRECT_SHAPE : "ViaDirectShape"
             * - CANVAS_DRAWABLE_LINE : "Line"
             * - CANVAS_LABEL_BLOC : "BlockLabel"
             * @type {String}
             */
            ItemType: null,

            /**
             * Holds the item itself. It will be drawn using the method associated with the
             * type defined in the ItemType member variable.
             * @type {Object}
             */
            Item: null
        }
        /**
         *
         */
    StylesManager = function() {
        this._styleMap = {};
    };


    StylesManager.STATE_DEFAULT = 0;
    StylesManager.STATE_HOVER = 1;
    StylesManager.STATE_ACTIVE = 2;

    StylesManager.prototype.selectorDestination = null;
    StylesManager.prototype.selectorDefaultDestination = null;
    StylesManager.prototype.selectorDestinationTitleBaseline = null;
    StylesManager.prototype.selectorShape = null;
    StylesManager.prototype.selectorTitleBaseline = null;


    /**
     *
     */
    StylesManager.prototype._styleMap = {};

    /**
     *
     * @param pName
     * @returns
     */
    StylesManager.prototype.getStyle = function(pName) {
        if (pName in this._styleMap) {
            return this._styleMap[pName];
        }
        return null;
    };

    /**
     * lookup for a style in stylesheets
     *
     * @param pName selector name
     * @param force force the lookup even if a style already exists in manager
     * @returns
     */
    StylesManager.prototype.lookupStyle = function(pName, force) {
        var styleObj;
        force = force !== null ? force : false;
        if ((styleObj = this.getStyle(pName)) && !force) return styleObj;
        var cssStyle;
        // lookup in css stylesheets
        var cssRule = CSSHelper.FindCssRuleBySelector(pName);
        if (cssRule && cssRule.style) {
            cssStyle = CSSHelper.CleanCSSObject(cssRule.style);
            if (cssStyle.length > 0) {
                var props = ["fontSize", "padding", "paddingLeft", "paddingRight", "paddingTop", "paddingBottom", "borderWidth", "maxHeight", "maxWidth", "maxFontSize"];

                for (var i = 0, len = props.length; i < len; i++) {
                    if (!(props[i] in cssStyle)) continue;

                    cssStyle[props[i]] = parseInt(cssStyle[props[i]]);
                    if (isNaN(cssStyle[props[i]])) cssStyle[props[i]] = this.setUndefined();
                }
                var styleObj = new CssProperties();
                var key;
                for (key in cssStyle) {
                    if (key in styleObj) styleObj[key] = cssStyle[key];
                }
                for (key in styleObj) {
                    if (styleObj[key] == false || styleObj[key] === null) styleObj[key] = this.setUndefined();
                }
                //this.setStyle( pName, styleObj );

                return styleObj;
            }
        }
        return null;
    };

    /**
     *
     * @param pName
     * @param pStyle
     */
    StylesManager.prototype.setStyle = function(pName, pStyle) {
        //    console.log( "setStyle " + pName );
        //    console.log( pStyle );
        this._styleMap[pName] = pStyle;
    };

    StylesManager.prototype.setUndefined = function() {
        return;
    };

    StylesManager.prototype.getState = function(pState) {
        var pseudoClassSelector = "";
        switch (pState) {
            case StylesManager.STATE_HOVER:
                pseudoClassSelector = ":hover";
                break;
            case StylesManager.STATE_ACTIVE:
                pseudoClassSelector = ":active";
                break;
        }
        return pseudoClassSelector;
    }

    StylesManager.prototype.getStyleNameForDestination = function(pDestinationId, pState) {
        return this.selectorDestination.replace(/\{id\}/, pDestinationId) + this.getState(pState);
    };

    StylesManager.prototype.getDestinationTitleBaselineStyleName = function(pDestinationId, pState) {
        return this.selectorDestinationTitleBaseline.replace(/\{id\}/, pDestinationId) + this.getState(pState);
    };

    StylesManager.prototype.getTitleBaselineStyleName = function(pState) {
        return this.selectorTitleBaseline + this.getState(pState);
    };

    StylesManager.prototype.getDefaultDestinationStyleName = function(pState) {
        return this.selectorDefaultDestination + this.getState(pState);
    };

    StylesManager.prototype.getShapeStyleName = function(pState) {
        return this.selectorShape + this.getState(pState);
    };

    /**
     *
     *
     * @require viadirect/model/Scene.js
     * @require viadirect/model/DrawableItem.js
     * @require viadirect/model/ResourcesHolder.js
     * @require viadirect/utils/StylesManager.js
     */

    /**
     * @constructor
     * @this {MapManipulator}
     * @memberof {viadirect.canvas.viewer}
     * @param {ViadirectMap}
     *                viadirectmap map object
     * @param {CanvasManager}
     *                canvasManager the canvas manager responsible to draw
     */
    function MapManipulator(viadirectMap, canvasManager, eventManager) {
        this._currentDestinationId = -1;
        this._currentOverDestinationId = -1;
        this.ViadirectMap = viadirectMap;
        this.Resources = viadirectMap.ResourcesMap;
        this.CanvasManager = canvasManager;
        this.Distance = 50;
        this.PathThickness = 20;
        this.Dimensions = {
            height: 75,
            width: 75
        };
        this._eventManager = eventManager;

        this._startingPoint = null;
        this._isPMR = false;
    };

    /**
     * @type StylesManager
     */
    MapManipulator.prototype._styleManager = new StylesManager();

    /**
     *
     * @param pBool
     * @returns {Boolean}
     */
    MapManipulator.prototype._displayItineraries = true;

    /**
     * @type String
     */
    MapManipulator.prototype._externalIdKey = "";


    /**
     * @return {Boolean}
     */
    MapManipulator.prototype.getDisplayItineraries = function() {
        return this._displayItineraries;
    };

    /**
     * @param {Boolean} pBool
     */
    MapManipulator.prototype.setDisplayItineraries = function(pBool) {
        this._displayItineraries = pBool;

        if (this.CanvasManager.Scene.ItemsList.length != 0)
            this.invalidateDraw();
    };

    MapManipulator.prototype.ScaleMode = 'fitInView';

    /**
     * @type {Floor}
     */
    MapManipulator.prototype._currentFloor = new Floor();

    /**
     *
     */
    MapManipulator.prototype.ColorsMap = {
        selected: new Color(164, 41, 41),
        over: new Color(128, 128, 128)
    };

    /**
     * @type {EventManagerDecorator}
     */
    MapManipulator.prototype._eventManager = null;


    /**
     * @type {string}
     */
    MapManipulator.prototype.FloorName = null;

    /**
     *
     */
    MapManipulator.prototype.Dimensions = null;

    MapManipulator.prototype.Distance = null;

    MapManipulator.prototype.Resources = null;

    MapManipulator.prototype._isPMR = false;

    /**
     * @type {Number}
     */
    MapManipulator.prototype.CurrentLanguageId = 0;

    /**
     * @type {Number}
     */
    MapManipulator.prototype._currentOverDestinationId = -1;

    /**
     *
     * @param {Number} shopId
     */
    MapManipulator.prototype.setStartingPointByShopId = function(shopId) {
        this.setStartingPoint(this.ViadirectMap.FindPointsAssociatedToDestinationID(shopId)[0]);
    };

    /**
     *
     * @param {Number} externalShopId
     */
    MapManipulator.prototype.setStartingPointByExternalShopId = function(externalShopId) {
        if (externalShopId !== null) {
            var destination = this.FindDestinationByExternalId(externalShopId, this._externalIdKey);
            if (destination) {
                this.setStartingPointByShopId(destination.Id);
                return;
            }
        }

        this.setStartingPoint(null);
    };

    /**
     *
     * @param {Number} pointId
     */
    MapManipulator.prototype.setStartingPointById = function(pointId) {
        this.setStartingPoint(this.ViadirectMap.FindPathPointById(pointId));
    };

    MapManipulator.prototype.setStartingPoint = function(point) {
        this._startingPoint = point;
    };

    /**
     * returns the starting path point
     * @returns {PathPoint}
     */
    MapManipulator.prototype.getStartingPoint = function() {
        return this._startingPoint;
    };

    /**
     * @returns {EventManagerDecorator}
     */
    MapManipulator.prototype.getEventManager = function() {
        return this._eventManager;
    };

    /**
     *
     * @param {EventManagerDecorator}
     *                pEventManager
     */
    MapManipulator.prototype.setEventManager = function(pEventManager) {
        this._eventManager = pEventManager;
        if (this._eventManager) {
            this._eventManager.bind("vdShapePicked", $.proxy(this.shapePicked, this));
            this._eventManager.bind("vdShapeOver", $.proxy(this.shapeOver, this));
            this._eventManager.bind("vdOrientationChange", $.proxy(this.orientationChanged, this));
        }
    };

    /**
     *
     * @param floorName
     */
    MapManipulator.prototype.Initialize = function() {
        if (this._currentFloorName)
            this.floorChanged(this._currentFloorName);
        else if (this.ViadirectMap.FloorList.length > 0) {
            this.floorChanged(this.ViadirectMap.FloorList[0].Name);
        }
    };

    /**
     * @type {ViadirectMap}
     */
    MapManipulator.prototype.ViadirectMap = new ViadirectMap();

    MapManipulator.prototype.selectedDestinations = [];

    /**
     *
     * @returns {Number[]}
     */
    MapManipulator.prototype.getSelectedDestinations = function() {
        return this.selectedDestinations;
    };

    /**
     *
     * @param {Number[]} pDestinations
     * @returns {Number[]}
     */
    MapManipulator.prototype.setSelectedDestinations = function(pDestinations) {
        this.selectedDestinations = pDestinations ? pDestinations : [];
        for (var i = 0, len = this.selectedDestinations.length, dest; i < len; i++) {
            if (isNaN(this.selectedDestinations[i])) {
                if (('fieldName' in this.selectedDestinations[i] || 'external' in this.selectedDestinations[i]) && 'id' in this.selectedDestinations[i]) {
                    dest = this.FindDestinationByExternalId(this.selectedDestinations[i].id, this.selectedDestinations[i].fieldName);
                    if (dest) {
                        this.selectedDestinations[i] = dest.Id;
                    } else {
                        this.selectedDestinations.splice(i, 1);
                        len--;
                    }
                }
            }
        }
        this.invalidateDraw();
        return this.selectedDestinations;
    };

    /**
     *
     * @param {string|Object} pValue
     */
    MapManipulator.prototype.getDestination = function(pValue) {
        var destination;
        var id;
        var isId = pValue !== null && !isNaN(pValue);
        var isObject = pValue !== null && !isId;
        if (isObject) {
            var fieldName = ('fieldName' in pValue) ? pValue.fieldName : null;
            if (('id' in pValue)) id = pValue.id;
            if (('externalId' in pValue)) id = pValue.externalId;
            if (('externalDestinationId' in pValue)) id = pValue.externalDestinationId;
            if (id) {
                destination = this.FindDestinationByExternalId(id, fieldName);
            }
        } else if (isId) {
            var d = this.ViadirectMap.DestinationList;
            var i = d.length;
            while (--i > -1)
                if (d[i].Id == id) {
                    destination = d[i];
                    break;
                }
        }
        return destination;
    };

    /**
     * draws the floor identified by floorName
     *
     * @param {string} floorName
     */
    MapManipulator.prototype.LoadFloorInCanvas = function(floorName) {
        //    console.log( "LoadFloorInCanvas" + floorName );
        var i, len, isSelectedShape, isOverShape, newShape, currentShape, currentId;
        var currentFloor = this.ViadirectMap.FindFloorByName(floorName);

        if (!currentFloor) {
            console.log("Can't draw inexistant floor");
            return;
        }

        this._currentFloorName = floorName;
        this._currentFloor = currentFloor;
        this.CanvasManager.Scene = new Scene();

        // Load Texture
        if (currentFloor.Texture)
            this.CanvasManager.Scene.AddItem(new DrawableItem({
                texture: currentFloor.Texture,
                width: currentFloor.TextureWidth,
                height: currentFloor.TextureHeight
            }, CANVAS_DRAWABLE_BACKGROUND_TEXTURE));

        // Draw selected destination in red
        var selectedShapes = [];
        selectedShapes = selectedShapes.concat(this.getShapesForDestinationId(this.getCurrentDestinationId()));
        if (this.selectedDestinations.length) {
            for (i = 0, len = this.selectedDestinations.length; i < len; i++) {
                selectedShapes = selectedShapes.concat(this.getShapesForDestinationId(this.selectedDestinations[i]));
            }
        }

        // MouseOver Shapes
        var selectedOverShapes = this.getShapesForDestinationId(this._currentOverDestinationId);

        var defaultShapeStyle = this._styleManager.getStyle(this._styleManager.getShapeStyleName(StylesManager.STATE_DEFAULT));
        var activeShapeStyle = this._styleManager.getStyle(this._styleManager.getShapeStyleName(StylesManager.STATE_ACTIVE));
        var hoverShapeStyle = this._styleManager.getStyle(this._styleManager.getShapeStyleName(StylesManager.STATE_HOVER));

        var defaultDestinationStyle = this._styleManager.getStyle(this._styleManager.getDefaultDestinationStyleName(StylesManager.STATE_DEFAULT));
        var activeDestinationStyle = this._styleManager.getStyle(this._styleManager.getDefaultDestinationStyleName(StylesManager.STATE_ACTIVE));
        var hoverDestinationStyle = this._styleManager.getStyle(this._styleManager.getDefaultDestinationStyleName(StylesManager.STATE_HOVER));

        var defaultBaselineStyle = this._styleManager.getStyle(this._styleManager.getTitleBaselineStyleName(StylesManager.STATE_DEFAULT));
        var activeBaselineStyle = this._styleManager.getStyle(this._styleManager.getTitleBaselineStyleName(StylesManager.STATE_ACTIVE));
        var hoverBaselineStyle = this._styleManager.getStyle(this._styleManager.getTitleBaselineStyleName(StylesManager.STATE_HOVER));
        var currentShapeStyle;
        var currentBaselineStyle;
        var destinationId, destination;

        // Draw All Shapes
        for (i = 0, len = currentFloor.ShapeList.length, currentId; i < len; i++) {
            currentShape = currentFloor.ShapeList[i];
            currentId = currentShape.Id;
            isSelectedShape = selectedShapes.indexOf(currentId) > -1;
            isOverShape = selectedOverShapes.indexOf(currentId) > -1;

            destination = this.ViadirectMap.FindDestinationById(currentShape.DestinationsList[0]);

            if (isSelectedShape) {

                // try to get an override shape for the destination
                currentShapeStyle = this._styleManager.getStyle(this._styleManager.getStyleNameForDestination(this.getCurrentDestinationId(), StylesManager.STATE_ACTIVE));
                if (!currentShapeStyle) {
                    currentShapeStyle = activeDestinationStyle;
                }

                // try to get an override baseline for the destination
                currentBaselineStyle = this._styleManager.getStyle(this._styleManager.getDestinationTitleBaselineStyleName(this.getCurrentDestinationId(), StylesManager.STATE_ACTIVE));
                if (!currentBaselineStyle) {
                    currentBaselineStyle = activeBaselineStyle;
                }

                currentShape = currentShape.Clone();
            } else if (isOverShape) {
                // try to get an override shape for the destination
                currentShapeStyle = this._styleManager.getStyle(this._styleManager.getStyleNameForDestination(this._currentOverDestinationId, StylesManager.STATE_HOVER));
                if (!currentShapeStyle) {
                    currentShapeStyle = hoverDestinationStyle;
                }
                currentShape = currentShape.Clone();

                currentBaselineStyle = this._styleManager.getStyle(this._styleManager.getDestinationTitleBaselineStyleName(this._currentOverDestinationId, StylesManager.STATE_HOVER));
                if (!currentBaselineStyle) {
                    currentBaselineStyle = hoverBaselineStyle;
                }
            } else {
                // normal case
                // try to get an override shape for the destination
                if (destination) {
                    currentShapeStyle = this._styleManager.getStyle(this._styleManager.getStyleNameForDestination(destination.Id, StylesManager.STATE_DEFAULT));
                    if (!currentShapeStyle) {
                        currentShapeStyle = defaultDestinationStyle;
                    }
                    //                console.log( "try for " + this._styleManager.getDestinationTitleBaselineStyleName( destination.Id, StylesManager.STATE_DEFAULT ) );
                    currentBaselineStyle = this._styleManager.getStyle(this._styleManager.getDestinationTitleBaselineStyleName(destination.Id, StylesManager.STATE_DEFAULT));

                    if (!currentBaselineStyle) {
                        currentBaselineStyle = defaultBaselineStyle;
                    } else {
                        //                    console.log( "found an override");
                        //                    console.log( currentBaselineStyle );
                    }
                } else {
                    currentShapeStyle = defaultShapeStyle;
                    //currentShapeStyle.backgroundColor = currentShape.Color;
                }
            }

            if ((isOverShape || isSelectedShape) || (currentShape.Color == null && currentShapeStyle.backgroundColor != null)) {
                currentShape.Color = currentShapeStyle.backgroundColor;
            }
            currentShape.StrokeWidth = currentShapeStyle.borderWidth;
            currentShape.StrokeColor = currentShapeStyle.borderColor;

            this.CanvasManager.Scene.AddItem(new DrawableItem(currentShape, CANVAS_DRAWABLE_VIADIRECT_SHAPE));

            // handling titleBaseLine 
            if (currentShape.TitleBaseline && currentShape.TitleBaseline.hasPoints()) {
                if (!currentShape.TitleBaseline.Label) {
                    destinationId = this.FindDestinationsIdAssociatedWithShapeId(currentShape.Id);
                    destination = this.ViadirectMap.FindDestinationById(destinationId[0]);
                    if (destination && !destination.IsHidden) {
                        currentShape.TitleBaseline.Label = destination.NameTranslations[this.CurrentLanguageId];
                    }
                }
                currentShape.TitleBaseline.CssPropertiesList = currentBaselineStyle;
                if (currentShape.TitleBaseline.Label && currentShape.TitleBaseline.Label.length) {
                    this.CanvasManager.Scene.AddItem(new DrawableItem(currentShape.TitleBaseline, CANVAS_LABEL_BLOC));
                    currentShape.TitleBaseline = this.SetBaselineTextPositionAndCSS(currentShape.TitleBaseline);
                }
            }
        }

        if (this._currentPath != null && this._displayItineraries) {
            var image, pointArray, point, limit, limit2;
            if (this.Resources.ArrowPattern == null)
                this.CreateArrowPattern(this.PathThickness);

            var prevSegment, currentSegment, nextSegment, nextSegment2;
            for (i = 0, len = this._currentPath.path.length - 1, limit = len - 1, limit2 = limit - 1; i < len; i++) {
                prevSegment = i > 0 ? this._currentPath.path[i - 1] : null;

                currentSegment = this._currentPath.path[i];

                nextSegment = i < len ? this._currentPath.path[i + 1] : null;
                nextSegment2 = i < (limit) ? this._currentPath.path[i + 2] : null;

                if (floorName != currentSegment.Floor.Name)
                    continue;

                if (prevSegment && nextSegment)
                    if (floorName != nextSegment.Floor.Name && floorName != prevSegment.Floor.Name) {
                        var pos2 = new Point(currentSegment.Position.X + 20, currentSegment.Position.Y);
                        var point = this.findThirdPoint(pos2, currentSegment.Position, this.Distance);

                        if (nextSegment.Floor.Position.Y > currentSegment.Floor.Position.Y)
                            image = this.Resources.EscalatorUp;
                        else
                            image = this.Resources.EscalatorDown;

                        this.CanvasManager.Scene.AddItem(new DrawableItem({
                            center: currentSegment.Position,
                            radius: this.PathThickness / 2
                        }, CANVAS_DRAWABLE_VIADIRECT_CIRCLE));
                        this.CanvasManager.Scene.AddItem(new DrawableItem({
                            Image: image,
                            Point: point,
                            Dimensions: this.Dimensions
                        }, CANVAS_DRAWABLE_PICTO));
                        continue;
                    }

                if (nextSegment && floorName != nextSegment.Floor.Name)
                    continue;

                pointArray = new Array();
                pointArray.push(currentSegment.Position);
                if (nextSegment)
                    pointArray.push(nextSegment.Position);
                this.CanvasManager.Scene.AddItem(new DrawableItem({
                    points: pointArray,
                    pattern: this.Resources.ArrowPattern,
                    thickness: this.PathThickness
                }, CANVAS_DRAWABLE_VIADIRECT_PATH));

                if (nextSegment2)
                    if (nextSegment.Floor.Name != nextSegment2.Floor.Name) {
                        point = this.findThirdPoint(currentSegment.Position, nextSegment.Position, this.Distance);

                        if (nextSegment2.Floor.Position.Y > nextSegment.Floor.Position.Y)
                            image = this.Resources.EscalatorUp;
                        else
                            image = this.Resources.EscalatorDown;

                        this.CanvasManager.Scene.AddItem(new DrawableItem({
                            Image: image,
                            Point: point,
                            Dimensions: this.Dimensions
                        }, CANVAS_DRAWABLE_PICTO));
                    }

                if (prevSegment)
                    if (prevSegment.Floor.Name != currentSegment.Floor.Name && nextSegment) {
                        point = this.findThirdPoint(nextSegment.Position, currentSegment.Position, this.Distance);

                        if (currentSegment.Floor.Position.Y > prevSegment.Floor.Position.Y)
                            image = this.Resources.EscalatorDown;
                        else
                            image = this.Resources.EscalatorUp;

                        this.CanvasManager.Scene.AddItem(new DrawableItem({
                            Image: image,
                            Point: point,
                            Dimensions: this.Dimensions
                        }, CANVAS_DRAWABLE_PICTO));
                    }
            }

            if (this.Resources.YouAreHere) {
                var currentSegment = this._currentPath.path[this._currentPath.path.length - 1];
                var startingPoint = this.getStartingPoint();
                var destination = this.ViadirectMap.FindDestinationAssociatedToPathPoint(startingPoint);
                if (!destination[0].HasCategory(CONSTANT_DOORS_CATEGORY))
                    if (currentSegment.Floor.Name == floorName) {
                        var point = new Point(currentSegment.Position.X, currentSegment.Position.Y - 40)
                        this.CanvasManager.Scene.AddItem(new DrawableItem({
                            Image: this.Resources.YouAreHere,
                            Point: point,
                            Dimensions: {
                                height: 68,
                                width: 200
                            }
                        }, CANVAS_DRAWABLE_BILBOARD));
                    }
            }
        }

        this._eventManager.trigger("vdFloorSelected", floorName);
    };

    MapManipulator.prototype.findThirdPoint = function(point1, point2, distance) {

        var mag = Math.sqrt((point2.X - point1.X) * (point2.X - point1.X) + (point2.Y - point1.Y) * (point2.Y - point1.Y));
        var P3x = point2.X + distance * (point2.X - point1.X) / mag;
        var P3y = point2.Y + distance * (point2.Y - point1.Y) / mag;

        return new Point(P3x, P3y);
    };

    MapManipulator.prototype.getShapesForDestinationId = function(pId) {
        var selectedShapesArray = [];
        // Draw selected destination in red
        if (pId !== null && pId != -1) {
            if (!this.ViadirectMap.DestinationList[pId])
                return;

            var destinationShapeList = this.ViadirectMap.DestinationList[pId].ShapeList;

            for (var i = 0, len = destinationShapeList.length; i < len; i++) {
                selectedShapesArray.push(destinationShapeList[i].Id);
            }
        }

        return selectedShapesArray;
    };

    /**
     * @param {BlocLabel} titleBaseline
     * @returns {BlocLabel}
     */
    MapManipulator.prototype.SetBaselineTextPositionAndCSS = function(titleBaseline) {

        var aPrime = new Point();
        var bPrime = new Point();
        var a = titleBaseline.PointList[0];
        var b = titleBaseline.PointList[1];
        var c = new Point(a.X, b.Y);
        var lineLength = CanvasHelper.GetLength(titleBaseline.PointList[0], titleBaseline.PointList[1]);

        var cssProperties = new CssProperties();
        cssProperties.fontSize = 0;
        cssProperties.fontFamily = "Verdana";
        cssProperties.color = "black";
        cssProperties.display = "block";
        cssProperties.maxFontSize = 300;

        cssProperties = $.extend({}, cssProperties, titleBaseline.CssPropertiesList);
        //    console.log( cssProperties );
        var metric;
        if (!cssProperties.fontSize) {

            do {
                cssProperties.fontSize++;
                this.CanvasManager.Canvas2dContext.font = cssProperties.fontSize + "px " + cssProperties.fontFamily;
                metric = this.CanvasManager.Canvas2dContext.measureText(titleBaseline.Label);
            } while (metric.width < lineLength && cssProperties.fontSize < cssProperties.maxFontSize);

            cssProperties.fontSize--;
        }

        if (titleBaseline.MaxTextHeight) {
            cssProperties.fontSize = Math.min(cssProperties.fontSize, titleBaseline.MaxTextHeight);
        }

        this.CanvasManager.Canvas2dContext.font = cssProperties.fontSize + "px " + cssProperties.fontFamily;

        metric = this.CanvasManager.Canvas2dContext.measureText(titleBaseline.Label);

        var aPrimeBprime = metric.width;
        var ab = CanvasHelper.GetLength(a, b);
        var bc = CanvasHelper.GetLength(b, c);
        var ac = CanvasHelper.GetLength(a, c);

        var halfHeight = (cssProperties.fontSize / 2) * .75;

        if (b.X < c.X) {
            aPrime.X = b.X + bc * (aPrimeBprime + (ab - aPrimeBprime) / 2) / ab;
            bPrime.X = b.X + bc * ((ab - aPrimeBprime) / 2) / ab;
        } else {
            aPrime.X = b.X - bc * (aPrimeBprime + (ab - aPrimeBprime) / 2) / ab;
            bPrime.X = b.X - bc * ((ab - aPrimeBprime) / 2) / ab;
        }

        var dir;
        if (a.Y < c.Y) {
            dir = 1;
            bPrime.Y = (a.Y + ac * (aPrimeBprime + (ab - aPrimeBprime) / 2) / ab);
            aPrime.Y = (a.Y + ac * ((ab - aPrimeBprime) / 2) / ab);
        } else {
            dir = -1;
            aPrime.Y = (a.Y - ac * ((ab - aPrimeBprime) / 2) / ab);
            bPrime.Y = (a.Y - ac * (aPrimeBprime + (ab - aPrimeBprime) / 2) / ab);
        }


        titleBaseline.StartingPoint = aPrime;
        titleBaseline.Angle = CanvasHelper.GetAngle(aPrime, bPrime);
        //    aPrime.Y += halfHeight * dir;
        titleBaseline.CssPropertiesList = cssProperties;

        return titleBaseline;
    };

    /**
     *
     * @param {Number} externalId
     * @param {String} externalIdKey
     */
    MapManipulator.prototype.FindDestinationByExternalId = function(externalId, externalIdKey) {

        var destinationList = this.ViadirectMap.DestinationList;
        var key;
        if (externalIdKey == null) externalIdKey = this._externalIdKey;
        for (key in destinationList) {
            if (destinationList[key] && !destinationList[key].IsHidden && destinationList[key].GetExternalId(externalIdKey) == externalId)
                return destinationList[key];
        }

        return null;
    };

    /**
     *
     */
    MapManipulator.prototype.FindDestinationsIdAssociatedWithShapeId = function(shapeId) {
        var resultDestinationsIdArray = new Array();
        var destinationList = this.ViadirectMap.DestinationList;
        var key, j, slen;
        var destShapeId;
        for (key in destinationList) {
            for (j = 0, slen = destinationList[key].ShapeList.length; j < slen; j++) {
                if (j in destinationList[key].ShapeList) {
                    if (!destinationList[key].ShapeList[j])
                        continue;

                    destShapeId = destinationList[key].ShapeList[j].Id;

                    if (destShapeId == shapeId)
                        resultDestinationsIdArray.push(destinationList[key].Id);
                }
            }
        }

        return resultDestinationsIdArray;
    };

    MapManipulator.prototype.orientationChanged = function(ev, angle) {
        if (this.ScaleMode == 'fitInView') {
            this.zoomFitInView();
        }
        this.CanvasManager.invalidateDraw();
    }

    MapManipulator.prototype.floorChanged = function(floorName) {
        this.CanvasManager.ResetTranslationsAndScaling();
        this.LoadFloorInCanvas(floorName);
        if (this.ScaleMode == 'fitInView') {
            this.zoomFitInView();
        }
        this.CanvasManager.invalidateDraw();

        //    do {
        //        this.CanvasManager.HandleZoomOut();
        //    } while (this.CanvasManager._textureWidth * this.CanvasManager.Scale > this.CanvasManager.CanvasWidth
        //            || this.CanvasManager._textureHeight * this.CanvasManager.Scale > this.CanvasManager.CanvasHeight);

        this._eventManager.trigger('vdFloorChanged', floorName);
    };

    MapManipulator.prototype.zoomFitInView = function() {
        var ratio, tx, ty;
        var tw = this._currentFloor.TextureWidth,
            th = this._currentFloor.TextureHeight,
            cw = this.CanvasManager.CanvasElement.width,
            ch = this.CanvasManager.CanvasElement.height;
        ratio = cw / tw;
        if (th * ratio > ch) ratio = ch / th;
        this.CanvasManager.FutureScale = ratio;
        tx = Math.max(0, (cw - tw * ratio) / 2) / ratio;
        ty = Math.max(0, (ch - th * ratio) / 2) / ratio;
        this.CanvasManager.setTranslatePos(new Point(tx, ty));
        this.CanvasManager.MinimumScaleLimit = Math.min(ratio, this.CanvasManager.MinimumScaleLimit);
    };



    MapManipulator.prototype.shapePicked = function(ev, pShape) {
        if (pShape != null && pShape.Selectable) {
            var destinationsId = this.FindDestinationsIdAssociatedWithShapeId(pShape.Id);
            this._currentShape = pShape;
            if (destinationsId.length == 0)
                return;
            var destinationId = destinationsId[0];
            var destination = this.ViadirectMap.DestinationList[destinationId];
            if (!destination.IsHidden) {
                this.setCurrentDestinationId(destinationId);
            }
        } else {
            this._currentShape = null;
            this.setCurrentDestinationId(-1);
        }
    };

    MapManipulator.prototype.selectDestination = function(destination) {
        if (destination) {
            var shapeId;
            if (this._currentShape != null) shapeId = this._currentShape.Id;
            else {
                var shapes = this.getShapesForDestinationId(destination.Id);
                shapeId = shapes.length ? shapes[0] : null;
            }

            if (!destination.IsHidden) {
                this._eventManager.trigger("vdDestinationPicked", {
                    destination: destination,
                    destinationId: destination.Id,
                    shapeId: shapeId,
                    canvasState: this.CanvasManager.getVisualState(),
                    manager: this,
                    toSimpleObject: function() {
                        return {
                            destination: this.destination.toSimpleObject(),
                            destinationId: this.destinationId,
                            canvasState: this.canvasState,
                            shapeId: this.shapeId,
                            manager: this.manager.toSimpleObject()
                        };
                    }
                });

                if (destination.Id != this._currentDestinationId) {
                    this.setCurrentDestinationId(destination.Id);
                }
            }
        } else {
            this.setCurrentDestinationId(null);
        }
    };

    MapManipulator.prototype.shapeOver = function(ev, pShape) {
        if (pShape && pShape.Selectable) {
            if (pShape != this.currentShapeOver) {
                this.currentShapeOver = pShape;
                var destinationsId = this.FindDestinationsIdAssociatedWithShapeId(pShape.Id);

                if (destinationsId.length == 0)
                    return;
                var destinationId = destinationsId[0];
                var destination = this.ViadirectMap.DestinationList[destinationId];
                if (!destination.IsHidden) {
                    this._eventManager.trigger("vdDestinationRollOver", {
                        destination: destination,
                        destinationId: destinationId,
                        shapeId: pShape.Id,
                        manager: this,
                        canvasState: this.CanvasManager.getVisualState(),
                        toSimpleObject: function() {
                            return {
                                destination: this.destination.toSimpleObject(),
                                destinationId: this.destinationId,
                                shapeId: this.shapeId,
                                canvasState: this.canvasState,
                                manager: this.manager.toSimpleObject()
                            };
                        }
                    });
                    this.setCurrentOverDestinationId(destinationId);
                }
            }
        } else {
            if (this.currentShapeOver != null) {
                this._eventManager.trigger("vdDestinationRollOut", {
                    destination: this.ViadirectMap.DestinationList[this._currentOverDestinationId],
                    destinationId: this._currentOverDestinationId,
                    shapeId: this.currentShapeOver.Id,
                    manager: this,
                    canvasState: this.CanvasManager.getVisualState(),
                    toSimpleObject: function() {
                        return {
                            destination: this.destination ? this.destination.toSimpleObject() : null,
                            destinationId: this.destinationId,
                            shapeId: this.shapeId,
                            canvasState: this.canvasState,
                            manager: this.manager.toSimpleObject()
                        };
                    }
                });
            }
            this.currentShapeOver = null;
            this.setCurrentOverDestinationId(-1);
        }
    };

    /**
     *
     * @param {Number}
     *                destinationId
     * @param {Boolean}
     *                center
     */
    MapManipulator.prototype.setCurrentDestinationId = function(destinationId, center) {
        var i, len;

        var destination = this.ViadirectMap.FindDestinationById(destinationId);
        if (destinationId == this._currentDestinationId) {
            this.selectDestination(destination);
            return; // end of the game: we don't want recursion loop or resources waste
        }


        if (!destination || (destination && (destination.IsHidden || destination.ShapeList.length == 0))) {
            // this.setCurrentPath( null );
            this._currentDestinationId = -1;
            this.getEventManager().trigger('vdDestinationUnpicked');
            this.invalidateDraw();
            return;
        }

        this._currentDestinationId = destinationId;

        var floorFound = false;
        for (i = 0, len = destination.ShapeList.length; i < len; i++) {
            if (destination.ShapeList[i].FloorName == this._currentFloorName) {
                floorFound = true;
                break;
            }
        }

        if (!floorFound && destination.ShapeList.length != 0) {
            this._currentFloorName = destination.ShapeList[0].FloorName;
        }

        var shapeId = null;
        var centerPoint = null;
        if (center) {
            if (destination) {
                for (i = 0, len = destination.ShapeList.length; i < len; i++) {
                    if (destination.ShapeList[i].FloorName == this._currentFloorName) {
                        shapeId = destination.ShapeList[i].Id;
                        break;
                    }
                }

                if (shapeId !== null) {
                    var currentFloor = null;
                    currentFloor = this.ViadirectMap.FindFloorByName(this._currentFloorName);

                    for (i = 0, len = currentFloor.ShapeList.length; i < len; i++) {
                        if (currentFloor.ShapeList[i].Id == shapeId) {
                            centerPoint = currentFloor.ShapeList[i].CenterPoint;
                            break;
                        }
                    }
                }
            }
        }

        this.selectDestination(destination);

        this.setCurrentPath(this._currentDestinationId);

        this.LoadFloorInCanvas(this._currentFloorName);

        if (centerPoint !== null && center)
            this.CanvasManager.CenterCamOnPoint(centerPoint);

        //this.CanvasManager.invalidateDraw();
        this.invalidateDraw();
    };

    MapManipulator.prototype.setCurrentPath = function(pDestId) {

        if (!this._displayItineraries) return;
        var endingPoints = this.ViadirectMap.FindPointsAssociatedToDestinationID(pDestId);

        if (this._startingPoint && endingPoints.length) {
            var bestPath;

            for (key in endingPoints) {
                var result = PathCalculator.CalculatePath(this.ViadirectMap.PathList, this._startingPoint, endingPoints[key], this._isPMR);

                if (!bestPath)
                    bestPath = result;

                //Hago check de nulls
                if (bestPath!==null && bestPath.length > result.length)
                    bestPath = result;
            }

            this._currentPath = bestPath;
            this._currentPath.destinationObject = this.ViadirectMap.FindDestinationById(pDestId);

            this._currentPath.endDestination = pDestId;


        } else {
            this._currentPath = null;
        }
        this._eventManager.trigger("vdPathCalculated", this._currentPath);
    };

    MapManipulator.prototype.getCurrentPath = function() {
        return this._currentPath;
    };

    /**
     *
     * @param {Number}
     *                destinationId
     * @param {Boolean}
     *                center
     */
    MapManipulator.prototype.setCurrentOverDestinationId = function(destinationId, center) {
        if (destinationId === this._currentOverDestinationId)
            return;
        this._currentOverDestinationId = destinationId;

        var destination = this.ViadirectMap.FindDestinationById(destinationId);
        var shapeId = null;
        var centerPoint = null;
        if (center) {
            if (destination) {
                for (var i = 0, len = destination.ShapeList.length; i < len; i++) {
                    if (destination.ShapeList[i].FloorName == this._currentFloorName) {
                        shapeId = destination.ShapeList[i].Id;
                        break;
                    }
                }

                if (shapeId !== null) {
                    var currentFloor = null;
                    currentFloor = this.ViadirectMap.FindFloorByName(this._currentFloorName);

                    for (var i = 0, len = currentFloor.ShapeList.length; i < len; i++) {
                        if (currentFloor.ShapeList[i].Id == shapeId) {
                            centerPoint = currentFloor.ShapeList[i].CenterPoint;
                            break;
                        }
                    }
                }
            }
        }

        this.LoadFloorInCanvas(this._currentFloorName);

        if (centerPoint !== null && center)
            this.CanvasManager.CenterCamOnPoint(centerPoint);

        this.CanvasManager.invalidateDraw();
    };

    MapManipulator.prototype.getCurrentLanguage = function() {
        return this.CurrentLanguageId;
    };

    MapManipulator.prototype.setPMRMode = function(PMR) {
        var needRedraw = false;

        if (PMR != this.isPMR)
            needRedraw = true;

        this._isPMR = PMR;

        if (needRedraw) {
            if (this._currentPath != null) {
                this.setCurrentPath(this._currentDestinationId);
                this.invalidateDraw();
            }
        }
    };

    MapManipulator.prototype.getPMRMode = function() {
        return this._isPMR;
    };

    MapManipulator.prototype.getCurrentDestinationId = function() {
        return this._currentDestinationId;
    };

    MapManipulator.prototype.setCurrentFloorName = function(currentFloorName) {
        this._currentFloorName = currentFloorName;

        if (this.CanvasManager.Scene.ItemsList.length != 0)
            this.invalidateDraw(this._currentFloorName);
    };

    MapManipulator.prototype.getCurrentFloorName = function() {
        return this._currentFloorName;
    };

    MapManipulator.prototype.CreateArrowPattern = function(width) {
        var myCanvas = document.createElement('canvas');
        myCanvas.width = width;
        myCanvas.height = (width * this.Resources.ArrowIcon.height) / this.Resources.ArrowIcon.width;
        var canvasContext = myCanvas.getContext("2d");
        canvasContext.drawImage(this.Resources.ArrowIcon, 0, 0, width, myCanvas.height);
        this.Resources.ArrowPattern = this.CanvasManager.CreatePattern(myCanvas, "repeat-y");
    }

    MapManipulator.prototype.getDestination = function(pValue) {
        var destination = null;

        if (typeof(pValue) == "object") {
            if (key in pValue && pValue.key)
                if (id in pValue && pValue.id) {
                    destination = this.FindDestinationByExternalId(pValue.id, pValue.key);
                }
        }

        if (typeof(pValue) == "number") {
            destination = this.ViadirectMap.DestinationList[pValue];
        }

        return destination;
    }

    MapManipulator.prototype._invalidateRes = null;
    MapManipulator.prototype.invalidateDraw = function() {
        if (this._invalidateRes == null) {
            this._invalidateRes = window.requestAnimFrame($.proxy(function(stamp) {
                this.LoadFloorInCanvas(this._currentFloorName);
                this.CanvasManager.invalidateDraw();
                this._invalidateRes = null;
            }, this));
        }
    }

    /**
     *
     * @returns {StylesManager}
     */
    MapManipulator.prototype.getStyleManager = function() {
        return this._styleManager;
    };

    /**
     *
     * @param {StylesManager} pManager
     */
    MapManipulator.prototype.setStyleManager = function(pManager) {
        this._styleManager = pManager;
    };


    MapManipulator.prototype.getExternalIdKey = function() {
        return this._externalIdKey;
    };

    MapManipulator.prototype.setExternalIdKey = function(pValue) {
        this._externalIdKey = pValue;
    };

    MapManipulator.prototype.toSimpleObject = function() {
            return {
                map: this.ViadirectMap.toSimpleObject(),
                currentFloor: this._currentFloor.Name,
                currentLanguage: this.CurrentLanguageId,
                externalIdKey: this._externalIdKey
            }
        }
        /**
         * @file CanvasManager.js
         * @package viadirect.view
         * @require viadirect/model/Point.js
         * @require viadirect/model/CssProperties.js
         */

    function CanvasManager() {
        this.CanvasElement = null;
        this.Canvas2dContext = null;
        this.TranslatePos = new Point();
        this.Scene = new Scene();
        this._elementInitialPosition = new Point();
        this._elementFinalPosition = new Point();
        this._pointToCenter = null;
        this._startDragOffset = new Point();
        this.GridTranslate = new Point(0, 0);
        this.TouchstartEvent = null;
        this._isDrawing = false;
        this._touchList = new Array();
        this._centerOnCursor = {
            oldPointOnCanvas: new Point(),
            originalCursorMouse: new Point(),
            isSet: false
        };
        this.PreviousTouchMoveLength = null;
        this.ZoomStep = 0;
        this.ClickMargin = 0.1;
    };

    CanvasManager.prototype = {

        /**
         * @type {Boolean}
         */
        _canDrawLabels: true,

        _interactionsEnabled: false,

        _controlsInteractionsEnabled: true,

        _destinationsUserInteractionsEnabled: true,

        _destinationsPickUserInteractionsEnabled: true,

        _canSelectOver: true,

        _minimumFontSizeThreshold: 10,

        MinimumScaleLimit: 0.01,

        MaximumScaleLimit: 2,
        /**
         * @type {jQueryElement}
         */
        CanvasElement: null,

        /**
         * @type {HTMLCanvasElement}
         */
        Canvas2dContext: null,

        /**
         * @type {number}
         */
        DefaultScaleValue: 1,

        /**
         * @type {number}
         */
        Scale: 1,

        /**
         * @type {number}
         */
        FutureScale: 1,

        /**
         * @type {number}
         */
        InitialScale: 1,

        /**
         * @type {number}
         */
        ScaleMultiplier: 0.9,

        /**
         * @type {number}
         */
        GestureScaleMultiplier: 0.95,

        /**
         * @type {Point}
         */
        TranslatePos: new Point(),

        /**
         * @type {Scene}
         */
        Scene: new Scene(),

        /**
         * @type {Point}
         */
        _elementInitialPosition: new Point(),

        /**
         * @type {Point}
         */
        _elementFinalPosition: new Point(),

        /**
         * @type {Point}
         */
        _pointToCenter: null,

        /**
         * @type {Point}
         */
        _centerOnCursor: {
            oldPointOnCanvas: new Point(),
            originalCursorMouse: new Point(),
            isSetisSet: false
        },
        /**
         *
         */
        _needNotify: false,
        /**
         * @type {boolean}
         */
        _leftClickDown: false,

        /**
         * @type {boolean}
         */
        _middleClickDown: false,

        /**
         * @type {boolean}
         */
        _rightClickDown: false,

        /**
         * @type {Point}
         */
        _startDragOffset: new Point(),

        /**
         * @type {number}
         */
        _canvasHeight: 0,

        /**
         * @type {number}
         */
        _canvasWidth: 0,

        /**
         * @type {number}
         */
        _textureWidth: 0,

        /**
         * @type {number}
         */
        _textureHeight: 0,

        /**
         * @type {number}
         */
        GridPitch: 100,

        /**
         * @type {Point}
         */
        GridTranslate: new Point(),

        /**
         * @type {number}
         */
        GridRotation: 0,

        /**
         * @type {boolean}
         */
        EditMode: false,

        /**
         * @type {EventManagerDecorator}
         */
        eventManager: null,

        /**
         *
         * @param {EventManagerDecorator}
         *            pEventManager
         */
        setEventManager: function(pEventManager) {
            this.eventManager = pEventManager;
            if (this.eventManager && this._interactionsEnabled)
                this._initializeEvents();
        },

        findTouchWithId: function(touches, touchId) {
            for (key in touches) {
                if (touches[key].identifier == touchId)
                    return touches[key];
            }

            return null;
        },
        _destroyEvents: function() {
            var pCanvas = $(this.CanvasElement);
            pCanvas
                .unbind("click mouseup mousemove mousedown mouseup mousewheel DOMMouseScroll MozMousePixelScroll wheel touchend touchstart touchmove touchleave");
        },
        _initializeEvents: function() {
            if (!this._interactionsEnabled)
                return;
            var pCanvas = $(this.CanvasElement);

            if (this._destinationsUserInteractionsEnabled) {
                if (!this.$vdClickHandler) {
                    this.$vdClickHandler = $.proxy(function(event) {

                        this.eventManager.trigger("vdClick", event);
                    }, this);
                }
                pCanvas.unbind("click", this.$vdClick).bind("click",
                    this.$vdClickHandler);
            }

            if (!this.$vdMouseUpHandler) {
                this.$vdMouseUpHandler = $.proxy(function(event) {
                    this.eventManager.trigger("vdMouseup", event);
                }, this);
            }
            pCanvas.unbind("mouseup", this.$vdMouseUpHandler).bind("mouseup",
                this.$vdMouseUpHandler);

            if (!this.$vdTouchend) {
                this.$vdTouchend = $.proxy(function(event) {
                    this._touchList = event.originalEvent.changedTouches;

                    if (this._touchList.length > 1) {
                        this._leftClickDown = false;
                        return;
                    }

                    var first = this._touchList[0];

                    var type = 'mouseup';

                    var finalEvent = new Object();
                    finalEvent.target = this.CanvasElement;
                    finalEvent.offsetX = first.pageX;
                    finalEvent.offsetY = first.pageY;
                    finalEvent.pageX = first.pageX;
                    finalEvent.pageY = first.pageY;
                    finalEvent.which = 0;
                    finalEvent.type = "mouseup";

                    if (this.TouchstartEvent.offsetX + this.TouchstartEvent.offsetX * this.ClickMargin >= finalEvent.offsetX && this.TouchstartEvent.offsetX - this.TouchstartEvent.offsetX * this.ClickMargin <= finalEvent.offsetX && this.TouchstartEvent.offsetY + this.TouchstartEvent.offsetY * this.ClickMargin >= finalEvent.offsetY && this.TouchstartEvent.offsetY - this.TouchstartEvent.offsetY * this.ClickMargin <= finalEvent.offsetY) {
                        this.eventManager.trigger("vdClick", finalEvent);
                    }

                    this.eventManager.trigger("vdMouseup", finalEvent);
                    // event.preventDefault();
                }, this);
            }
            pCanvas.unbind("touchend", this.$vdTouchend).bind("touchend",
                this.$vdTouchend);

            if (!this.$vdTouchstart) {
                this.$vdTouchstart = $.proxy(function(event) {
                    this._touchList = event.originalEvent.changedTouches;
                    var first = this._touchList[0];

                    if (this._touchList.length > 1) {
                        this._leftClickDown = false;
                        event.stopPropagation();
                        event.stopImmediatePropagation();
                        event.preventDefault();
                        event.cancelBubble = true;
                        return;
                    }

                    var finalEvent = new Object();
                    finalEvent.target = this.CanvasElement;
                    finalEvent.offsetX = first.pageX;
                    finalEvent.offsetY = first.pageY;
                    finalEvent.pageX = first.pageX;
                    finalEvent.pageY = first.pageY;
                    finalEvent.which = 1;
                    finalEvent.identifier = first.identifier;
                    finalEvent.type = "mousedown";

                    this.TouchstartEvent = finalEvent;
                    this.eventManager.trigger("vdMousedown", finalEvent);
                    event.stopPropagation();
                    event.stopImmediatePropagation();
                    event.preventDefault();
                    event.cancelBubble = true;
                    return false;
                }, this);

            }
            pCanvas.unbind("touchstart", this.$vdTouchstart).bind("touchstart",
                this.$vdTouchstart);

            if (!this.$vdMousemove) {
                this.$vdMousemove = $.proxy(function(event) {
                    this.eventManager.trigger("vdMousemove", {
                        event: event,
                        isTouch: false
                    });
                }, this);
            }
            $(this.CanvasElement).unbind("mousemove", this.$vdMousemove).bind("mousemove",
                this.$vdMousemove);

            if (!this.$vdMousedown) {
                this.$vdMousedown = $.proxy(function(event) {
                    this.eventManager.trigger("vdMousedown", event);
                    event.stopPropagation();
                    event.stopImmediatePropagation();
                    event.preventDefault();
                    event.cancelBubble = true;
                    return false;
                }, this);
            }
            pCanvas.unbind("mousedown", this.$vdMousedown).bind("mousedown",
                this.$vdMousedown);

            if (!this.$vdTouchmove) {
                this.$vdTouchmove = $
                    .proxy(
                        function(event) {
                            this._touchList = event.originalEvent.changedTouches;

                            if (this._touchList.length > 1) {
                                this._leftClickDown = false;

                                if (this.PreviousTouchMoveLength === null) {
                                    var finger1 = new Point(
                                        this._touchList[0].pageX,
                                        this._touchList[0].pageY);
                                    var finger2 = new Point(
                                        this._touchList[1].pageX,
                                        this._touchList[1].pageY);
                                    this.PreviousTouchMoveLength = CanvasHelper
                                        .GetLength(finger1, finger2);
                                } else {
                                    var finger1 = new Point(
                                        this._touchList[0].pageX,
                                        this._touchList[0].pageY);
                                    var finger2 = new Point(
                                        this._touchList[1].pageX,
                                        this._touchList[1].pageY);
                                    var currentTouchMoveLength = CanvasHelper
                                        .GetLength(finger1, finger2);

                                    var result = {};
                                    result.gesture = true;
                                    var hasEffect = false;

                                    if (currentTouchMoveLength >= this.PreviousTouchMoveLength + this.ZoomStep) {
                                        result.originalEvent = {
                                            wheelDelta: 2
                                        };
                                        hasEffect = true;
                                    }

                                    if (currentTouchMoveLength <= this.PreviousTouchMoveLength + this.ZoomStep) {
                                        result.originalEvent = {
                                            wheelDelta: -2
                                        };
                                        hasEffect = true;
                                    }

                                    this.PreviousTouchMoveLength = currentTouchMoveLength;

                                    if (hasEffect) {
                                        result.centerpoint = this
                                            .FindCenterBetweenTwoPoints(
                                                finger1.X,
                                                finger1.Y,
                                                finger2.X,
                                                finger2.Y);
                                        this.eventManager.trigger(
                                            "vdMousewheel", result);
                                    }
                                }
                                return;
                            }

                            this.PreviousTouchMoveLength = null;

                            var first = this.findTouchWithId(
                                this._touchList,
                                this.TouchstartEvent.identifier);

                            if (!first)
                                return;

                            var finalEvent = new Object();
                            finalEvent.target = this.CanvasElement;
                            finalEvent.offsetX = first.pageX;
                            finalEvent.offsetY = first.pageY;
                            finalEvent.pageX = first.pageX;
                            finalEvent.pageY = first.pageY;
                            finalEvent.type = "mousemove";

                            event.preventDefault();
                            this.eventManager.trigger("vdMousemove", {
                                event: finalEvent,
                                isTouch: true
                            });
                        }, this);
            }

            pCanvas.unbind("touchmove", this.$vdTouchmove).bind("touchmove",
                this.$vdTouchmove);

            if (!this.$vdMouseout) {
                this.$vdMouseout = $.proxy(function(event) {
                    this.eventManager.trigger("vdMouseout", event);
                }, this);
            }
            pCanvas.unbind("mouseout", this.$vdMouseout).bind("mouseout",
                this.$vdMouseout);

            if (!this.$vdTouchleave) {
                this.$vdTouchleave = $.proxy(function(event) {
                    this.eventManager.trigger("vdMouseout", event.originalEvent);
                }, this);
            }
            pCanvas.unbind("touchleave", this.$vdTouchleave).bind("touchleave",
                this.$vdTouchleave);

            if (!this.$vdMousewheel) {
                this.$vdMousewheel = $
                    .proxy(
                        function(event) {
                            // this.eventManager.trigger("vdMousewheel", {
                            // originalEvent
                            // : event.originalEvent, gesture : true,
                            // centerpoint : new
                            // Point ( event.originalEvent.clientX,
                            // event.originalEvent.clientY ) });

                            var target = event.originalEvent.target || event.originalEvent.srcElement,
                                rect = target.getBoundingClientRect(),
                                offsetX = event.originalEvent.clientX - rect.left,
                                offsetY = event.originalEvent.clientY - rect.top;

                            this.eventManager.trigger("vdMousewheel", {
                                originalEvent: event.originalEvent,
                                centerpoint: new Point(offsetX, offsetY)
                            });
                            event.stopPropagation();
                            event.stopImmediatePropagation();
                            event.preventDefault();
                            event.cancelBubble = true;
                            return false;
                        }, this);
            }

            if (this._controlsInteractionsEnabled) {
                pCanvas.unbind("mousewheel DOMMouseScroll MozMousePixelScroll wheel",
                    this.$vdMousewheel).bind(
                    "mousewheel DOMMouseScroll MozMousePixelScroll wheel",
                    this.$vdMousewheel);
            }
            if (this._destinationsPickUserInteractionsEnabled) {
                if (!this.$vdMousePicking) {
                    this.$vdMousePicking = $.proxy(this.mapPicked, this);
                }
                this.eventManager.unbind("mousePicking", this.$vdMousePicking).bind(
                    "mousePicking", this.$vdMousePicking);
            }
        },

        /**
         *
         * @param {HTMLCanvasElement}
         */
        FindCenterBetweenTwoPoints: function(p1x, p1y, p2x, p2y) {
            return new Point((p1x + p2x) / 2, (p1y + p2y) / 2);
        },

        /**
         *
         * @param {HTMLCanvasElement}
         */
        setCanvasElement: function(pEle) {
            this.CanvasElement = pEle;
        },

        /**
         * @param {string}
         *            context - context of the canvas
         */
        Initialize: function(context) {
            if (!context)
                context = "2d";

            var jQueriedCanvas = $(this.CanvasElement);
            this.Canvas2dContext = this.CanvasElement.getContext(context);

            // this.TrackTransforms( this.Canvas2dContext );

            this.CanvasElement.height = jQueriedCanvas.height();
            this.CanvasElement.width = jQueriedCanvas.width();

            if (this._canvasHeight)
                this.CanvasElement.height = this._canvasHeight;
            else
                this._canvasHeight = this.CanvasElement.height;

            if (this._canvasWidth)
                this.CanvasElement.width = this._canvasWidth;
            else
                this._canvasWidth = this.CanvasElement.width;

            this.ResetTranslationsAndScaling();

            if (!("$_handleVdClick" in this)) {
                this.$_handleVdClick = $.proxy(function(event, originalEvent) {
                    this.HandleClick(originalEvent);
                }, this);
            }
            this.eventManager.unbind("vdClick", this.$_handleVdClick).bind("vdClick", this.$_handleVdClick);

            if (!("$_handleVdMouseup" in this)) {
                this.$_handleVdMouseup = $.proxy(function(event, originalEvent) {
                    this.HandleMouseUp(originalEvent);
                }, this);
            }
            this.eventManager.unbind("vdMouseup", this.$_handleVdMouseup).bind("vdMouseup", this.$_handleVdMouseup);

            if (!("$_handleVdMousedown" in this)) {
                this.$_handleVdMousedown = $.proxy(function(event, originalEvent) {
                    this.HandleMouseDown(originalEvent);
                }, this);
            }
            this.eventManager.unbind("vdMousedown", this.$_handleVdMousedown).bind("vdMousedown", this.$_handleVdMousedown);

            if (!("$_handleVdMousemove" in this)) {
                this.$_handleVdMousemove = $.proxy(function(event, originalEvent) {
                    this.HandleMouseMove(originalEvent);
                }, this);
            }
            this.eventManager.unbind("vdMousemove", this.$_handleVdMousemove).bind("vdMousemove", this.$_handleVdMousemove);

            if (!("$_handleVdMouseout" in this)) {
                this.$_handleVdMouseout = $.proxy(
                    function(event, originalEvent) {
                        this.HandleMouseOut(originalEvent);
                    }, this);
            }
            this.eventManager.unbind("vdMouseout", this.$_handleVdMouseout).bind("vdMouseout", this.$_handleVdMouseout);

            if (!("$_handleVdMouseWheel" in this)) {
                this.$_handleVdMouseWheel = $.proxy(
                    function(event, object) {
                        if (!object.originalEvent) {
                            return;
                        }
                        cursorPos = object.centerpoint;

                        var delta = 0;
                        if (object.originalEvent.wheelDelta || object.originalEvent.detail) {
                            delta = Math
                                .max(-1,
                                    Math
                                    .min(
                                        1, (object.originalEvent.wheelDelta || -object.originalEvent.detail)));
                        } else if (object.originalEvent.scale) {
                            delta = Math.max(-1, Math.min(1,
                                object.originalEvent.scale - 1));
                        } else if (object.originalEvent.deltaY)
                            delta = object.originalEvent.deltaY;

                        if (!this.EditMode)
                            if (delta > 0) {
                                this.HandleZoomIn(object.gesture, cursorPos);
                            } else if (delta < 0) {
                            this.HandleZoomOut(object.gesture, cursorPos);
                        } else {
                            if (delta > 0) {
                                this.GridPitch++;
                            } else if (delta < 0) {
                                this.GridPitch--;
                            }

                            if (this.GridPitch == 0)
                                this.GridPitch++;
                            this.invalidateDraw();
                        }
                    }, this);
            }

            this.eventManager.unbind("vdMousewheel", this.$_handleVdMouseWheel).bind("vdMousewheel", this.$_handleVdMouseWheel);

            if (!("$_handleVdUIZoomIn" in this)) {
                this.$_handleVdUIZoomIn = $.proxy(function(ev) {
                    this.HandleZoomIn();
                }, this);
            }

            this.eventManager.unbind("vdUIZoomIn", this.$_handleVdUIZoomIn).bind("vdUIZoomIn", this.$_handleVdUIZoomIn);

            if (!("$_handleVdZoomOut" in this)) {
                this.$_handleVdZoomOut = $.proxy(function(ev) {
                    this.HandleZoomOut();
                }, this);
            }

            this.eventManager.unbind("vdUIZoomOut", this.$_handleVdZoomOut).bind(
                "vdUIZoomOut", this.$_handleVdZoomOut);

            if (!("$_vdResize" in this)) {
                this.$_handleVdResize = $.proxy(function(event) {
                    this.setCanvasHeight($(this.CanvasElement).height());
                    this.SetCanvasWidth($(this.CanvasElement).width());
                    this.invalidateDraw();
                }, this);
            }

            this.eventManager.unbind("vdResize", this.$_handleVdResize).bind("vdResize", this.$_handleVdResize);
        },

        /**
         * @param {number}
         *            height - context of the canvas
         */
        setCanvasHeight: function(height) {
            this._canvasHeight = height;

            if (this.CanvasElement)
                this.CanvasElement.height = this._canvasHeight;
        },

        /**
         * @returns {number}
         */
        getCanvasHeight: function() {
            return this._canvasHeight;
        },

        SetCanvasWidth: function(width) {
            this._canvasWidth = width;

            if (this.CanvasElement)
                this.CanvasElement.width = this._canvasWidth;
        },

        GetCanvasWidth: function() {
            return this._canvasWidth;
        },

        setDrawLabels: function(pVal) {
            this._canDrawLabels = Boolean(pVal);
        },

        Draw: function() {
            if (this._isDrawing) {
                return;
            }

            this._isDrawing = true;
            var i, len, backgroundTexture;
            this.ClearCanvas();

            this._textureHeight = 0;
            this._textureWidth = 0;
            // Find the background Texture Size in the scene
            for (i = 0, len = this.Scene.ItemsList.length; i < len; i++) {
                if (this.Scene.ItemsList[i].ItemType == CANVAS_DRAWABLE_BACKGROUND_TEXTURE) {
                    backgroundTexture = this.Scene.ItemsList[i].Item.texture;
                    this._textureHeight = this.Scene.ItemsList[i].Item.height;
                    this._textureWidth = this.Scene.ItemsList[i].Item.width;
                    break;
                }
            }

            this.Canvas2dContext.save();
            this.InitializeDrawning();
            var translation = new Point(this.TranslatePos.X, this.TranslatePos.Y);
            for (i = 0, len = this.Scene.ItemsList.length; i < len; i++) {
                var currentItem = this.Scene.ItemsList[i];

                switch (currentItem.ItemType) {
                    case CANVAS_DRAWABLE_BACKGROUND_TEXTURE:
                        this.ApplyBackgroundTexture(currentItem.Item.texture);
                        break;
                    case CANVAS_DRAWABLE_VIADIRECT_SHAPE:
                        this.DrawCustomShape(currentItem.Item.PointList,
                            currentItem.Item.Color);
                        break;
                    case CANVAS_DRAWABLE_LINE:
                        this.DrawLine(currentItem.Item);
                        break;
                    case CANVAS_DRAWABLE_VIADIRECT_PATHPOINT:
                        this.DrawPoint(currentItem.Item);
                        break;
                    case CANVAS_DRAWABLE_VIADIRECT_PATH:
                        this.DrawPath(currentItem.Item.points,
                            currentItem.Item.pattern, currentItem.Item.thickness);
                        break;
                    case CANVAS_DRAWABLE_VIADIRECT_CIRCLE:
                        this.DrawCircle(currentItem.Item.center,
                            currentItem.Item.radius, "#B02127");
                        break;
                    case CANVAS_DRAWABLE_PICTO:
                        this.DrawImage(currentItem.Item.Image, currentItem.Item.Point,
                            currentItem.Item.Dimensions);
                        break;
                    case CANVAS_DRAWABLE_BILBOARD:
                        this.DrawImage(currentItem.Item.Image, currentItem.Item.Point,
                            currentItem.Item.Dimensions);
                        break;
                    case CANVAS_LABEL_BLOC:
                        if (!this._canDrawLabels)
                            continue;

                        if (currentItem.Item.CssPropertiesList.Display == "none")
                            break;

                        this.DrawText(currentItem.Item.StartingPoint,
                            currentItem.Item.Angle, currentItem.Item.Label,
                            currentItem.Item.CssPropertiesList);
                        break;
                }
            }

            if (this.EditMode)
                this.DrawGrid();

            this.Canvas2dContext.restore();
            this._isDrawing = false;

        },
        /**
         *
         * @param {Point}
         *            mousePoint
         * @returns {Shape}
         */
        MousePickInScene: function(mousePoint) {
            var i, len;
            var backgroundTexture, currentItem;
            this._textureHeight = 0;
            this._textureWidth = 0;

            // Find the background Texture Size in the scene
            for (i = 0, len = this.Scene.ItemsList.length; i < len; i++) {
                if (this.Scene.ItemsList[i].ItemType == CANVAS_DRAWABLE_BACKGROUND_TEXTURE) {
                    backgroundTexture = this.Scene.ItemsList[i].Item.texture;
                    this._textureHeight = this.Scene.ItemsList[i].Item.height;
                    this._textureWidth = this.Scene.ItemsList[i].Item.width;
                    break;
                }
            }

            this.Canvas2dContext.save();
            this.InitializeDrawning();
            for (i = 0, len = this.Scene.ItemsList.length; i < len; i++) {
                currentItem = this.Scene.ItemsList[i];

                switch (currentItem.ItemType) {
                    case CANVAS_DRAWABLE_VIADIRECT_SHAPE:
                        if (this.TestShape(currentItem.Item.PointList, mousePoint)) {
                            this.Canvas2dContext.restore();
                            return currentItem.Item;
                        }
                        break;
                }
            }
            this.Canvas2dContext.restore();

            return false;
        },

        DrawPoint: function(point, color) {

            if (!this.Canvas2dContext)
                return;

            this.StartDrawing();

            // begin custom shape
            this.Canvas2dContext.beginPath();

            this.Canvas2dContext.moveTo(point.X - 2, point.Y);
            this.Canvas2dContext.lineTo(point.X + 2, point.Y);
            this.Canvas2dContext.moveTo(point.X, point.Y - 2);
            this.Canvas2dContext.lineTo(point.X, point.Y + 2);

            this.Canvas2dContext.lineWidth = 8;
            if (!color)
                this.Canvas2dContext.strokeStyle = 'red';
            else
                this.Canvas2dContext.strokeStyle = color;
            this.Canvas2dContext.stroke();

            this.StopDrawing();
        },

        DrawImage: function(image, point, dimensionImage) {

            if (!this.Canvas2dContext)
                return;

            this.StartDrawing();

            var height = image.height;
            var width = image.width;

            if (dimensionImage) {
                height = dimensionImage.height;
                width = dimensionImage.width;
            }

            var X = point.X - width / 2;
            var Y = point.Y - height / 2;

            this.Canvas2dContext.drawImage(image, X, Y, width, height);

            this.StopDrawing();
        },

        DrawLine: function(pointsArray, color) {

            if (!this.Canvas2dContext)
                return;

            this.StartDrawing();

            // begin custom shape
            this.Canvas2dContext.beginPath();

            // We place the first Point of the shape
            if (pointsArray.length > 0)
                this.Canvas2dContext.moveTo(pointsArray[0].X, pointsArray[0].Y);

            // And draw the rest
            for (var i = 1, len = pointsArray.length; i < len; i++) {
                this.Canvas2dContext.lineTo(pointsArray[i].X, pointsArray[i].Y);
            }

            // Stroke here
            this.Canvas2dContext.lineWidth = 5;
            if (!color)
                this.Canvas2dContext.strokeStyle = 'red';
            else
                this.Canvas2dContext.strokeStyle = color;
            this.Canvas2dContext.stroke();

            this.StopDrawing();
        },

        DrawPath: function(line, pattern, width) {

            if (!this.Canvas2dContext)
                return;

            this.StartDrawing();

            var length = CanvasHelper.GetLength(line[0], line[1]);

            var angle = CanvasHelper.GetAngle(line[0], line[1]) - Math.PI / 2;

            this.Canvas2dContext.translate(line[0].X, line[0].Y);
            this.Canvas2dContext.rotate(angle);

            this.Canvas2dContext.translate(-width / 2, 0);
            this.Canvas2dContext.beginPath();

            this.Canvas2dContext.moveTo(0, 0);

            this.Canvas2dContext.lineTo(0, length);
            this.Canvas2dContext.lineTo(0 + width, length);
            this.Canvas2dContext.lineTo(0 + width, 0);

            this.Canvas2dContext.closePath();

            this.Canvas2dContext.fillStyle = pattern;
            this.Canvas2dContext.fill();

            this.StopDrawing();
        },

        DrawCircle: function(center, radius, color) {

            if (!this.Canvas2dContext)
                return;

            this.StartDrawing();

            this.Canvas2dContext.beginPath();
            this.Canvas2dContext.arc(center.X, center.Y, radius, 0, 2 * Math.PI);

            this.Canvas2dContext.stroke();

            this.Canvas2dContext.fillStyle = color;
            this.Canvas2dContext.fill();

            this.StopDrawing();
        },

        DrawText: function(point, angle, text, cssProperties) {

            if (!this.Canvas2dContext)
                return;

            if ((cssProperties.fontSize * this.Scale) < this._minimumFontSizeThreshold)
                return;
            if (!text)
                text = "testText1";

            if (cssProperties.textTransform == "uppercase")
                text = text.toUpperCase();
            else if (cssProperties.textTransform == "lowercase")
                text = text.toLowerCase();

            this.StartDrawing();
            var pointX = point.X;
            var pointY = point.Y + Math.floor(cssProperties.fontSize / 2);

            this.Canvas2dContext.translate(point.X, point.Y);
            this.Canvas2dContext.rotate(angle);
            this.Canvas2dContext.translate(-point.X, -point.Y);

            if (cssProperties.backgroundColor) {
                var length = this.Canvas2dContext.measureText(text).width + 2 + cssProperties.paddingLeft + cssProperties.paddingRight;
                var height = cssProperties.fontSize + 2 + cssProperties.paddingTop + cssProperties.paddingBottom;
                var rectOriginX = pointX - cssProperties.paddingLeft - 1;
                var rectOriginY = pointY + cssProperties.paddingTop + 1 - height;

                this.Canvas2dContext.beginPath();
                this.Canvas2dContext.rect(rectOriginX, rectOriginY, length, height);
                this.Canvas2dContext.fillStyle = cssProperties.backgroundColor;
                this.Canvas2dContext.fill();
            }

            if (cssProperties.borderWidth && cssProperties.borderColor) {
                this.Canvas2dContext.lineWidth = cssProperties.borderWidth;
                this.Canvas2dContext.strokeStyle = cssProperties.borderColor;
                this.Canvas2dContext.stroke();
            }

            this.Canvas2dContext.fillStyle = cssProperties.color;
            this.Canvas2dContext.font = cssProperties.fontSize + "px " + cssProperties.fontFamily;
            this.Canvas2dContext.textBaseline = 'bottom';
            this.Canvas2dContext.fillText(text, pointX, pointY);

            // debugging
            // var oLineWidth = this.Canvas2dContext.lineWidth;
            // var oStrokeStyle = this.Canvas2dContext.strokeStyle;
            //
            // this.Canvas2dContext.moveTo( point.X, point.Y);
            // this.Canvas2dContext.lineWidth = 2;
            // this.Canvas2dContext.strokeStyle = "#CC0000";
            // this.Canvas2dContext.lineTo(
            // pointX+this.Canvas2dContext.measureText(text).width, point.Y);
            // this.Canvas2dContext.stroke();
            //
            // this.Canvas2dContext.lineWidth = oLineWidth;
            // this.Canvas2dContext.strokeStyle =oStrokeStyle;
            // end debug

            this.StopDrawing();
        },

        DrawCustomShape: function(pointsArray, fillColor) {

            if (!this.Canvas2dContext)
                return;

            this.StartDrawing();

            this.Canvas2dContext.lineJoin = "round";
            // begin custom shape
            this.Canvas2dContext.beginPath();

            // We place the first Point of the shape
            if (pointsArray.length > 0)
                this.Canvas2dContext.moveTo(pointsArray[0].X, pointsArray[0].Y);

            // And draw the rest
            for (var i = 1, len = pointsArray.length; i < len; i++) {
                this.Canvas2dContext.lineTo(pointsArray[i].X, pointsArray[i].Y);
            }

            // complete custom shape
            this.Canvas2dContext.closePath();

            if (!fillColor)
                this.Canvas2dContext.fillStyle = 'red';
            else if (typeof(fillColor) == 'object') {
                if (fillColor.A !== null) {
                    this.Canvas2dContext.fillStyle = 'rgba(' + fillColor.R + ',' + fillColor.G + ',' + fillColor.B + ', ' + (fillColor.A / 255) + ')';
                } else {
                    this.Canvas2dContext.fillStyle = 'rgb(' + fillColor.R + ',' + fillColor.G + ',' + fillColor.B + ')';
                }
            } else if (typeof(fillColor) == 'string')
                this.Canvas2dContext.fillStyle = fillColor;

            this.Canvas2dContext.fill();

            // Stroke here
            this.Canvas2dContext.lineWidth = 1;
            this.Canvas2dContext.strokeStyle = 'black';
            this.Canvas2dContext.stroke();

            this.StopDrawing();
        },

        ApplyBackgroundTexture: function(image) {

            if (!this.Canvas2dContext)
                return;

            if (!image)
                return;

            this.StartDrawing();

            try {
                this.Canvas2dContext.drawImage(image, 0, 0, this._textureWidth,
                    this._textureHeight);
            } catch (e) {
                // if( e instanceof TypeMismatchError) console.log(
                // "TypeMismatchError");
                // else if ( e instanceof InvalidStateError ) console.log (
                // "InvalidStateError" );
                // else if ( e instanceof IndexSizeError ) console.log (
                // "IndexSizeError" );
                // else if ( e instanceof SecurityError ) console.log (
                // "SecurityError" );
                console.log(e);
            }

            this.StopDrawing();
        },

        TestShape: function(pointsArray, mousePoint) {

            this.StartDrawing();
            // begin custom shape

            this.Canvas2dContext.beginPath();

            // We place the first Point of the shape
            if (pointsArray.length > 0)
                this.Canvas2dContext.moveTo(pointsArray[0].X, pointsArray[0].Y);

            // And draw the rest
            for (var i = 1, len = pointsArray.length; i < len; i++) {
                this.Canvas2dContext.lineTo(pointsArray[i].X, pointsArray[i].Y);
            }

            // complete custom shape
            this.Canvas2dContext.closePath();

            if (this.Canvas2dContext.isPointInPath(mousePoint.X, mousePoint.Y)) {
                this.StopDrawing();
                return true;
            }

            this.StopDrawing();

            return false;
        },

        CenterCamOnPoint: function(point) {
            this.setTranslatePos(new Point(-(point.X - (this._canvasWidth / this.Scale) / 2), -(point.Y - (this._canvasHeight / this.Scale) / 2)));
        },

        HandleClick: function(event) {
            var mouseCoords = this.GetMouseCoords(event),
                mouseX = mouseCoords.X,
                mouseY = mouseCoords.Y;
            // var mouseXY = RGraph.getMouseXY(event);
            // var mouseX = mouseXY[0];
            // var mouseY = mouseXY[1];

            if (!(mouseX < 0 || mouseX > this._canvasWidth || mouseY < 0 || mouseY > this._canvasHeight)) {

                if (!this.EditMode)
                    this.eventManager.trigger("mousePicking", new Point(mouseX,
                        mouseY));
            }
        },

        HandleMouseDown: function(event) {
            var oe = "originalEvent" in event ? event.originalEvent : event;
            var mouseCoords = this.GetMouseCoords(oe),
                mouseX = mouseCoords.X,
                mouseY = mouseCoords.Y;
            // var mouseXY = RGraph.getMouseXY(event);
            // var mouseX = mouseXY[0];
            // var mouseY = mouseXY[1];

            var x = mouseX / this.Scale - this.TranslatePos.X;
            var y = mouseY / this.Scale - this.TranslatePos.Y;
            this._elementInitialPosition.X = this.TranslatePos.X;
            this._elementInitialPosition.Y = this.TranslatePos.Y;
            this._startDragOffset = new Point(x, y);

            if (event.which == 1 || event.which === 0) {
                this._leftClickDown = true;

                if (this.EditMode) {
                    CanvasHelper
                        .rotationMatrice(new Point(x, y), this.GridRotation);

                    var pixelPicked = new Point();
                    pixelPicked.X = mouseX / this.Scale - this.TranslatePos.X;
                    pixelPicked.Y = mouseY / this.Scale - this.TranslatePos.Y;

                    var globalPixelPicked = new Point();
                    globalPixelPicked.X = mouseX;
                    globalPixelPicked.Y = mouseY;

                    var rotatedGlobalPixelPicked = new Point();
                    rotatedGlobalPixelPicked = CanvasHelper.changementRepere(
                        globalPixelPicked, this.GridTranslate,
                        this.GridRotation);

                    var griddedPixelPicked = new Point();
                    griddedPixelPicked.X = Math.round(rotatedGlobalPixelPicked.X / this.GridPitch) * this.GridPitch;
                    griddedPixelPicked.Y = Math.round(rotatedGlobalPixelPicked.Y / this.GridPitch) * this.GridPitch;

                    var griddedRotatedPixelPicked = new Point();
                    griddedRotatedPixelPicked = CanvasHelper.rotationMatrice(
                        griddedPixelPicked, -this.GridRotation);

                    var griddedRotatedTranslatedPixelPicked = new Point();
                    griddedRotatedTranslatedPixelPicked.X = griddedRotatedPixelPicked.X - (-this.GridTranslate.X);
                    griddedRotatedTranslatedPixelPicked.Y = griddedRotatedPixelPicked.Y - (-this.GridTranslate.Y);

                    var finalPixelPicked = new Point();
                    finalPixelPicked.X = griddedRotatedTranslatedPixelPicked.X / this.Scale - this.TranslatePos.X;
                    finalPixelPicked.Y = griddedRotatedTranslatedPixelPicked.Y / this.Scale - this.TranslatePos.Y;

                    var line = new DrawableItem(new Array(), CANVAS_DRAWABLE_LINE);

                    line.Item.push(finalPixelPicked);
                    this.Scene.ItemsList.push(line);
                }

            }
            if (event.which == 2) {
                this._middleClickDown = true;
            }
            if (event.which == 3) {
                var xGrid = mouseX / this.Scale - this.GridTranslate.X;
                var yGrid = mouseY / this.Scale - this.GridTranslate.Y;
                this._startDragOffset = new Point(xGrid, yGrid);
                this._rightClickDown = true;
                this._elementInitialPosition.X = this.GridTranslate.X;
                this._elementInitialPosition.Y = this.GridTranslate.Y;
            }


        },

        FindCurrentLine: function() {

            var last = null;

            for (var i = 0, len = this.Scene.ItemsList.length; i < len; i++) {
                if (this.Scene.ItemsList[i].ItemType == CANVAS_DRAWABLE_LINE)
                    last = i;
            }

            return last;
        },

        /**
         *
         * @param event
         */
        HandleMouseUp: function(event) {

            if (event.which == 1 || event.which === 0) {
                this._leftClickDown = false;

                if (this.EditMode) {
                    var indiceCurrentLine = this.FindCurrentLine();
                    var content = {};
                    content.line = this.Scene.ItemsList[indiceCurrentLine].Item;
                    this.eventManager.trigger("vdLineDrawn", content);
                }
            }

            if (event.which == 2) {
                this._middleClickDown = false;
            }

            if (event.which == 3) {
                this._rightClickDown = false;
            }
        },

        GetMouseCoords: function(event) {
            var target = event.target || event.srcElement,
                rect = target
                .getBoundingClientRect(),
                cX = "clientX" in event ? event.clientX : (event.pageX - window.scrollX),
                cY = "clientY" in event ? event.clientY : (event.pageY - window.scrollY),
                mouseX = cX - rect.left,
                mouseY = cY - rect.top;
            return new Point(mouseX, mouseY);
        },
        /**
         *
         * @param event
         */
        HandleMouseMove: function(object) {
            var event = object.event;
            var oe = "originalEvent" in event ? event.originalEvent : event;
            var mouseCoords = this.GetMouseCoords(oe),
                mouseX = mouseCoords.X,
                mouseY = mouseCoords.Y;

            this._elementFinalPosition.X = mouseX / this.Scale - this._startDragOffset.X;
            this._elementFinalPosition.Y = mouseY / this.Scale - this._startDragOffset.Y;

            if (this._leftClickDown && this._controlsInteractionsEnabled) {
                if (!this.EditMode) {
                    var distanceX = this._elementFinalPosition.X - this._elementInitialPosition.X;
                    var distanceY = this._elementFinalPosition.Y - this._elementInitialPosition.Y;
                    this.setTranslatePos(new Point(this._elementInitialPosition.X + distanceX, this._elementInitialPosition.Y + distanceY));

                    this.invalidateDraw();
                    return;
                } else {
                    var indiceCurrentLine = this.FindCurrentLine();

                    var pixelPicked = new Point();
                    pixelPicked.X = mouseX / this.Scale - this.TranslatePos.X;
                    pixelPicked.Y = mouseY / this.Scale - this.TranslatePos.Y;

                    var globalPixelPicked = new Point();
                    globalPixelPicked.X = mouseX;
                    globalPixelPicked.Y = mouseY;

                    var rotatedGlobalPixelPicked = new Point();
                    rotatedGlobalPixelPicked = CanvasHelper.changementRepere(
                        globalPixelPicked, this.GridTranslate,
                        this.GridRotation);

                    var griddedPixelPicked = new Point();
                    griddedPixelPicked.X = Math.round(rotatedGlobalPixelPicked.X / this.GridPitch) * this.GridPitch;
                    griddedPixelPicked.Y = Math.round(rotatedGlobalPixelPicked.Y / this.GridPitch) * this.GridPitch;

                    var griddedRotatedPixelPicked = new Point();
                    griddedRotatedPixelPicked = CanvasHelper.rotationMatrice(
                        griddedPixelPicked, -this.GridRotation);

                    var griddedRotatedTranslatedPixelPicked = new Point();
                    griddedRotatedTranslatedPixelPicked.X = griddedRotatedPixelPicked.X - (-this.GridTranslate.X);
                    griddedRotatedTranslatedPixelPicked.Y = griddedRotatedPixelPicked.Y - (-this.GridTranslate.Y);

                    var finalPixelPicked = new Point();
                    finalPixelPicked.X = griddedRotatedTranslatedPixelPicked.X / this.Scale - this.TranslatePos.X;
                    finalPixelPicked.Y = griddedRotatedTranslatedPixelPicked.Y / this.Scale - this.TranslatePos.Y;

                    this.Scene.ItemsList[indiceCurrentLine].Item[1] = finalPixelPicked;
                    this.invalidateDraw();
                    return;
                }
            }

            if (this._middleClickDown) {
                this.GridRotation = CanvasHelper.GetAngle(
                    this._elementInitialPosition, this._elementFinalPosition);
                this.invalidateDraw();
                return;
            }

            if (this._rightClickDown) {
                var distanceX = this._elementFinalPosition.X - this._elementInitialPosition.X;
                var distanceY = this._elementFinalPosition.Y - this._elementInitialPosition.Y;
                this.GridTranslate.X = this._elementInitialPosition.X + distanceX;
                this.GridTranslate.Y = this._elementInitialPosition.Y + distanceY;
                this.invalidateDraw();
                return;
            }

            if (this._destinationsUserInteractionsEnabled && !object.isTouch) {

                // trying to detect shape rollover now
                if (!(mouseX < 0 || mouseX > this._canvasWidth || mouseY < 0 || mouseY > this._canvasHeight)) {

                    var overShape = this
                        .MousePickInScene(new Point(mouseX, mouseY))
                        // if( overShape ) {
                    this.eventManager.trigger("vdShapeOver", overShape);
                    // }
                }
            }
        },

        HandleZoomOut: function(gesture, point) {

            if (!point) {
                point = new Point();
                point.X = this._canvasWidth / 2;
                point.Y = this._canvasHeight / 2;
            }

            this._pointToCenter = point;
            this._centerOnCursor.isSet = true;
            this._centerOnCursor.oldPointOnCanvas.X = (point.X / this.Scale) - this.TranslatePos.X;
            this._centerOnCursor.oldPointOnCanvas.Y = (point.Y / this.Scale) - this.TranslatePos.Y;
            this._centerOnCursor.originalCursorMouse = point;

            if (!gesture)
                this.FutureScale = Math.max(this.MinimumScaleLimit,
                    this.FutureScale * this.ScaleMultiplier);
            else
                this.FutureScale = Math.max(this.MinimumScaleLimit,
                    this.FutureScale * this.GestureScaleMultiplier);

            this.invalidateDraw();
        },

        HandleZoomIn: function(gesture, point) {

            if (!point) {
                point = new Point();
                point.X = this._canvasWidth / 2;
                point.Y = this._canvasHeight / 2;
            }

            this._pointToCenter = point;
            this._centerOnCursor.isSet = true;
            this._centerOnCursor.oldPointOnCanvas.X = (point.X / this.Scale) - this.TranslatePos.X;
            this._centerOnCursor.oldPointOnCanvas.Y = (point.Y / this.Scale) - this.TranslatePos.Y;
            this._centerOnCursor.originalCursorMouse = point;

            if (!gesture)
                this.FutureScale = Math.min(this.MaximumScaleLimit, this.FutureScale / this.ScaleMultiplier);
            else
                this.FutureScale = Math.min(this.MaximumScaleLimit, this.FutureScale / this.GestureScaleMultiplier);

            this.invalidateDraw();
        },

        HandleMouseOut: function(event) {
            this._leftClickDown = false;
            this._middleClickDown = false;
        },

        ResetTranslationsAndScaling: function() {
            this.setScale(this.DefaultScaleValue);
            this.FutureScale = this.DefaultScaleValue;
            this.ScaleMultiplier = 0.9;
            this.GestureScaleMultiplier = 0.95;
            this.setTranslatePos(new Point(0, 0));
        },

        InitializeDrawning: function() {
            var translateX = this.TranslatePos.X,
                translateY = this.TranslatePos.Y;
            if (this._textureWidth > this._canvasWidth / this.Scale) {
                if (this.TranslatePos.X > 0) {
                    translateX = 0;
                } else if (this.TranslatePos.X + this._textureWidth < this._canvasWidth / this.Scale) {
                    translateX = this._canvasWidth / this.Scale - this._textureWidth;
                }
            } else {
                if (this.TranslatePos.X < 0) {
                    translateX = 0;
                } else if (this.TranslatePos.X + this._textureWidth > this._canvasWidth / this.Scale) {
                    translateX = this._canvasWidth / this.Scale - this._textureWidth;
                }
            }

            if (this._textureHeight > this._canvasHeight / this.Scale) {
                if (this.TranslatePos.Y > 0) {
                    translateY = 0;
                } else if (this.TranslatePos.Y + this._textureHeight < this._canvasHeight / this.Scale) {
                    translateY = this._canvasHeight / this.Scale - this._textureHeight;
                }
            } else {
                if (this.TranslatePos.Y < 0) {
                    translateY = 0;
                } else if (this.TranslatePos.Y + this._textureHeight > this._canvasHeight / this.Scale) {
                    translateY = this._canvasHeight / this.Scale - this._textureHeight;
                }
            }



            if (this._centerOnCursor.isSet === true) {
                translateX = this._centerOnCursor.originalCursorMouse.X / this.Scale - this._centerOnCursor.oldPointOnCanvas.X;
                translateY = this._centerOnCursor.originalCursorMouse.Y / this.Scale - this._centerOnCursor.oldPointOnCanvas.Y;
                this._centerOnCursor.isSet = false;
            }

            this.setTranslatePos(new Point(translateX, translateY));
            this.Canvas2dContext.scale(this.Scale, this.Scale);
            this.Canvas2dContext.translate(this.TranslatePos.X, this.TranslatePos.Y);
        },

        /**
         *
         * @param {MapManipulator}
         *            mapManipulator
         */
        DrawDestinationTooltip: function(mapManipulator) {
            var destination = mapManipulator.ViadirectMap.DestinationList[mapManipulator
                .getCurrentDestinationId()];

            var content = "";
            content += '<img id="logo" src="' + destination.LogoUrl + '" />';
            content += '<h1 id="title" >' + destination.Name + '</h1>';
            content += '<h2 id="category" >' + destination.Category[0] + '</h2>';
            content += '<span id="description" >' + destination.DescriptionTranslations[0] + '</span>';
        },

        StartDrawing: function() {
            this.Canvas2dContext.save();
        },

        StopDrawing: function() {
            this.Canvas2dContext.restore();
        },

        DrawGrid: function() {
            this.Canvas2dContext.save();
            this.Canvas2dContext.beginPath();
            this.Canvas2dContext.translate(this.GridTranslate.X,
                this.GridTranslate.Y);

            this.Canvas2dContext.rotate(-this.GridRotation);

            for (var i = 0; i <= 100 * this.GridPitch; i = i + this.GridPitch) {
                this.Canvas2dContext.moveTo(i, 0);
                this.Canvas2dContext.lineTo(i, 100 * this.GridPitch);
                this.Canvas2dContext.moveTo(0, i);
                this.Canvas2dContext.lineTo(100 * this.GridPitch, i);
            }

            this.Canvas2dContext.lineWidth = 1;
            this.Canvas2dContext.strokeStyle = 'black';
            this.Canvas2dContext.stroke();
            this.Canvas2dContext.restore();
        },

        ClearCanvas: function() {
            this.Canvas2dContext.clearRect(0, 0, this._canvasWidth,
                this._canvasHeight);
        },

        FindCenterPoint: function(point) {
            this._pointToCenter = new Point();
            this._pointToCenter.X = (this._canvasWidth / this.Scale / 2) - this.TranslatePos.X;
            this._pointToCenter.Y = (this._canvasHeight / this.Scale / 2) - this.TranslatePos.Y;
        },

        mapPicked: function(event, mouse) {
            var shape = this.MousePickInScene(mouse);

            if (shape !== false) {
                if (!shape.Selectable)
                    return;

                this.eventManager.trigger("vdShapePicked", shape);
            } else {
                this.eventManager.trigger("vdShapePicked", null);
            }
        },

        CreatePattern: function(image, value) {
            return this.Canvas2dContext.createPattern(image, value);
        },

        _invalidateRes: null,

        _invalidateDrawHandler: function(stamp) {

            this.setScale(this.FutureScale);
            this.Draw();
            if (this._needNotify) {
                this.eventManager.trigger("vdDrawn", this.getVisualState());
                this._needNotify = false;
            }
            this._invalidateRes = null;
        },
        $_invalidateDraw: null,
        invalidateDraw: function() {
            if (this._invalidateRes == null) {
                if (this.$_invalidateDraw == null) {
                    this.$_invalidateDraw = $.proxy(this._invalidateDrawHandler, this);
                }
                this._invalidateRes = window.requestAnimFrame(this.$_invalidateDraw);
            }
        },

        getControlsInteractionsEnabled: function() {
            return this._controlsInteractionsEnabled;
        },
        getDestinationsInteractionsEnabled: function() {
            return this._destinationsUserInteractionsEnabled;
        },
        setControlsInteractionsEnabled: function(pValue) {
            var different = pValue != this._controlsInteractionsEnabled;
            this._controlsInteractionsEnabled = pValue;
            if (different) {
                this._destroyEvents();
                this._initializeEvents();
            }
        },
        setTranslatePos: function(point) {
            this._needNotify = this._needNotify || this.TranslatePos.X != point.X || this.TranslatePos.Y != point.Y;
            this.TranslatePos = point;
        },
        setScale: function(pValue) {
            this._needNotify = this._needNotify || this.Scale != pValue;
            this.Scale = pValue
        },
        setDestinationsPickInteractionsEnabled: function(pValue) {
            var different = pValue != this._destinationsUserInteractionsEnabled;
            this._destinationsPickUserInteractionsEnabled = pValue;
            if (different) {
                this._destroyEvents();
                this._initializeEvents();
            }
        },
        setDestinationsInteractionsEnabled: function(pValue) {
            var different = pValue != this._destinationsUserInteractionsEnabled;
            this._destinationsUserInteractionsEnabled = pValue;
            if (different) {
                this._destroyEvents();
                this._initializeEvents();
            }
        },

        getInteractionsEnabled: function() {
            return this._interactionsEnabled;
        },

        setInteractionsEnabled: function(pValue) {
            this._interactionsEnabled = pValue;
            if (this.eventManager) {
                if (this._interactionsEnabled) {
                    this._initializeEvents();
                } else {
                    this._destroyEvents();
                }
            }
        },
        /**
         * returns an object which gives a state of the visual object about the map
         */
        getVisualState: function() {
            var obj = {};
            var r = this.CanvasElement.getBoundingClientRect();
            obj.TranslatePos = this.TranslatePos;
            obj.Scale = this.Scale;
            obj.floorX = r.left + this.TranslatePos.X * this.Scale;
            obj.floorY = r.top + this.TranslatePos.Y * this.Scale;
            obj.floorWidth = this._textureWidth * this.Scale;
            obj.floorHeight = this._textureHeight * this.Scale;
            obj.floorRight = r.width - (obj.floorX + obj.floorWidth);
            return obj;
        }
    };
    /**
     *
     */

    /**
     *
     * @param jQuery
     *                pContext jQuery selector which will be used to dispatch events
     */
    function LoadingQueue(pContext) {
        this._context = pContext;
        this._stack = [];

        this.bind(LoadingQueue.ITEM_START, $.proxy(this._loadItemStart, this));
    }

    LoadingQueue.QUEUE_START = "loadingQueueStart";
    LoadingQueue.QUEUE_FINISHED = "loadingQueueFinished";
    LoadingQueue.ITEM_START = "loadingQueueItemStart";
    LoadingQueue.ITEM_FINISHED = "loadingQueueItemFinished";
    LoadingQueue.ITEM_ERROR = "loadingQueueItemErrorFinished";

    LoadingQueue.prototype.defaultSettings = function() {
        return {
            success: this._loadFinished,
            error: this._loadError,
            context: this
        };
    };

    LoadingQueue.prototype._isLoading = false;

    LoadingQueue.prototype._buildItemCallback = function(pUrl, pSettings, pLoadedCallback, pErrorCallback) {
        var self = this;
        return function() {
            var settings = $.extend({}, self.defaultSettings(), pSettings, true);
            settings.url = pUrl;

            self.dispatchEvent(LoadingQueue.ITEM_START, settings);
            var xhr = $.ajax(settings);
        };
    };

    /**
     * adds a
     *
     * @param pUrl
     * @param pSettings
     */
    LoadingQueue.prototype.add = function(pUrl, pSettings, pLoadedCallback, pErrorCallback) {

        var callback = this._buildItemCallback(pUrl, pSettings, pLoadedCallback, pErrorCallback);
        this._stack.push({
            callback: callback,
            url: pUrl,
            success: pLoadedCallback,
            error: pErrorCallback
        });
    };

    LoadingQueue.prototype.hasUrl = function(pUrl) {
        var i, len;
        for (i = 0, len = this._stack.length; i < len; i++) {
            if (this._stack[i].url == pUrl)
                return i;
        }
        return -1;
    };

    /**
     * adds a
     *
     * @param pUrl
     * @param pSettings
     */
    LoadingQueue.prototype.prepend = function(pUrl, pSettings, pLoadedCallback, pErrorCallback) {

        var callback = this._buildItemCallback(pUrl, pSettings, pLoadedCallback, pErrorCallback);

        var o;

        var index = this.hasUrl(pUrl);
        if (index == -1) {
            o = {
                callback: callback,
                url: pUrl,
                success: pLoadedCallback,
                error: pErrorCallback
            };
        } else {
            o = this._stack.splice(index, 1)[0];
        }

        if (this._currentItem == null || (!this._currentItem.url != o.url)) {
            this._stack.unshift(o);
        }
        //        else {
        // we're currently loading so we need to insert our object at the index
        // 1
        // in order to be directly the next item to be loaded after the current
        // one
        // this._stack.splice(1, 0, o);
        //    }

    };

    LoadingQueue.prototype._svgLoaded = function(callback) {
        //console.log( pData );
        return function(pData) {
            var pData = document.body.appendChild(pData.documentElement);
            document.body.removeChild(pData);
            callback(pData, this);
        };

    };

    LoadingQueue.prototype.addImage = function(pUrl, pLoadedCallback, pErrorCallback) {
        var self = this;
        //    if( pUrl.match( /\.svg$/i)) {
        //        console.log( "svg loading" );
        //        return this.add( pUrl,{}, this._svgLoaded( pLoadedCallback ), pErrorCallback );
        //    }

        var callback = function() {
            var image = new Image();

            if (pUrl.indexOf('file://') != 0) {
                image.crossOrigin = "anonymous";
            }

            image.onload = function() {
                var f = function() {
                    var ieBug = this.width == 0 && this.height == 0;
                    if (ieBug) {
                        // force rendering to obtain image dimensions
                        document.body.appendChild(this);
                        this.width = this.clientWidth;
                        this.height = this.clientHeight;
                    }
                    this.onload = null;
                    this.onerror = null;
                    this.onabort = null;
                    if (ieBug) {
                        document.body.removeChild(this);
                    }
                    self._loadImageFinished(this);
                };
                window.requestAnimFrame($.proxy(f, this));
            };
            image.onerror = function() {
                this.onload = null;
                this.onerror = null;
                this.onabort = null;
                self._loadImageError(this);
            };
            console.log("launch callback load image " + pUrl);
            self.dispatchEvent(LoadingQueue.ITEM_START, {});
            image.src = pUrl;
        };

        this._stack.push({
            callback: callback,
            url: pUrl,
            success: pLoadedCallback,
            error: pErrorCallback
        });

    };

    LoadingQueue.prototype._loadImageFinished = function(img) {
        this._isLoading = false;
        console.log("LoadingQueue::_loadImageFinished " + img);
        if (this._currentItem.success) {
            this._currentItem.success(img, this);
        }
        this.dispatchEvent(LoadingQueue.ITEM_FINISHED, this._currentItem);
        this._nextItem();
    };

    LoadingQueue.prototype._loadImageError = function(img) {
        console.log("_loadImageError " + img);
        if (this._currentItem.error) {
            this._currentItem.error(img, this);
        }
        this.dispatchEvent(LoadingQueue.ITEM_ERROR, this._currentItem);
        this._nextItem();
    };

    LoadingQueue.prototype.start = function() {
        console.log("LoadingQueue::start");
        if (!this._started) {
            this._started = true;
            this._nextItem();
        }
    };

    LoadingQueue.prototype._loadItemStart = function(ev) {
        this._isLoading = true;
    };

    /**
     * @private
     */
    LoadingQueue.prototype._loadFinished = function(pData) {
        console.log("LoadingQueue::_loadFinished " + this._currentItem.url);
        this._isLoading = false;
        if (this._currentItem.success) {
            this._currentItem.success(pData, this);
        }
        this.dispatchEvent(LoadingQueue.ITEM_FINISHED, this._currentItem);
        this._nextItem();
    };

    LoadingQueue.prototype._loadItemError = function() {
        this._isLoading = false;
        if (this._currentItem.error) {
            this._currentItem.error(pData, this);
        }
        this.dispatchEvent(LoadingQueue.ITEM_ERROR, this._currentItem);
        this._nextItem();
    };

    LoadingQueue.prototype._nextItem = function() {
        if (this._stack.length) {
            var next = this._stack.shift();
            if (next) {
                this._currentItem = next;
                this._currentItem.callback();
            } else {
                this._currentItem = null;
            }
        } else {
            this._currentItem = null;
            this._queueFinished();
        }
    };

    /**
     * dispatch an event
     *
     * @param pEvent
     * @param pDatas
     */
    LoadingQueue.prototype.dispatchEvent = function(pEvent, pDatas) {
        this._context.trigger(pEvent, pDatas);
    };

    LoadingQueue.prototype.bind = function(pEventType, pCallback) {
        if (this._context)
            this._context.bind.apply(this._context, arguments);
        return this;
    };

    LoadingQueue.prototype.unbind = function(pEventType, pCallback) {
        if (this._context)
            this._context.unbind.apply(this._context, arguments);
        return this;
    };

    /**
     * when all items are loaded
     */
    LoadingQueue.prototype._queueFinished = function() {

        this._stack = [];
        this._started = false;
        this.dispatchEvent(LoadingQueue.QUEUE_FINISHED, this);
    };

    LoadingQueue.prototype.getCurrentItem = function() {
        return this._currentItem;
    };

    LoadingQueue.prototype.length = function() {
        return this._stack.length;
    };


    /**
     * @constructor
     */
    function InitializeCategoryInstance(pCategory, pCategoryJson) {
        this.category = pCategory;
        this.categoryJson = pCategoryJson;
    }

    /**
     * @type Category
     */
    InitializeCategoryInstance.prototype.category = new Category();

    /**
     * @type Object
     */
    InitializeCategoryInstance.prototype.categoryJson = {};

    /**
     *
     */
    InitializeCategoryInstance.prototype.execute = function() {

        this.category.Name = JsonNavigator.walkPath(this.categoryJson, CATEGORY_PATH_TO_NAME);

        this.category.Id = JsonNavigator.walkPath(this.categoryJson, CATEGORY_PATH_TO_ID);

        var translations = JsonNavigator.walkPath(this.categoryJson, CATEGORY_PATH_TO_TEXT_RESSOURCES);

        for (var i = 0, len = translations.length; i < len; i++) {
            this.category.NameTranslations.push(JsonNavigator.walkPath(translations[i], TEXT_RESSOURCES_PATH_TO_NAME_TRANSLATION_TEXT));
        }
    };
    /**
     *
     * @constructor
     *
     */
    function InitializeTopicInstance(pTopic, pJson) {
        this.topic = pTopic;
        this.topicJson = pJson;
    }



    InitializeTopicInstance.prototype.topic = new Topic();

    InitializeTopicInstance.prototype.topicJson = {};

    InitializeTopicInstance.prototype.execute = function() {
        this.topic.Name = JsonNavigator.walkPath(this.topicJson, TOPIC_PATH_TO_NAME);

        this.topic.Id = JsonNavigator.walkPath(this.topicJson, TOPIC_PATH_TO_ID);

        var translations = JsonNavigator.walkPath(this.topicJson, TOPIC_PATH_TO_TEXT_RESSOURCES);

        for (var i = 0, len = translations.length; i < len; i++) {
            this.topic.NameTranslations.push(JsonNavigator.walkPath(translations[i], TEXT_RESSOURCES_PATH_TO_NAME_TRANSLATION_TEXT));
        }

        var color = JsonNavigator.walkPath(this.topicJson, TOPIC_PATH_TO_COLOR);

        if (color)
            this.topic.Color = CanvasHelper.convertPackedColorToColor(JsonNavigator.walkPath(this.topicJson, TOPIC_PATH_TO_PACKED_COLOR));
        else
            this.topic.Color = null;

    };
    /**
     * @constructor
     * @param {Destination}
     *                pDestination
     * @param {Object}
     *                pBuildingDestination
     * @param {Object}
     *                pDestinationJson
     * @param {ViadirectMap}
     *                pMap
     */
    function InitializeDestinationInstance(pDestination, pBuildingDestination, pMap) {
        this.destination = pDestination;

        this.buildingData = pBuildingDestination;

        this.destinationJson = pBuildingDestination;

        this.map = pMap;

    }

    /**
     * @type Destination
     */
    InitializeDestinationInstance.prototype.destination = new Destination();

    /**
     * @type ViadirectMap
     */
    InitializeDestinationInstance.prototype.map = new ViadirectMap();

    /**
     *
     */
    InitializeDestinationInstance.prototype.execute = function() {
        var i, len;
        this.destination.Name = JsonNavigator.walkPath(this.buildingData, DESTINATION_FROM_ROOT_PATH_TO_NAME);

        var translations = JsonNavigator.walkPath(this.buildingData, DESTINATION_FROM_ROOT_PATH_TO_TRANSLATIONS);

        for (var i = 0, len = translations.length; i < len; i++) {
            this.destination.NameTranslations.push(JsonNavigator.walkPath(translations[i],
                TEXT_RESSOURCES_PATH_TO_NAME_TRANSLATION_TEXT));
        }

        this.destination.LogoUrl = this.buildingData.LogoMap;
        var descriptions = JsonNavigator.walkPath(this.buildingData, DESTINATION_FROM_ROOT_PATH_TO_DESCRIPTION);

        if (descriptions)
            for (i = 0, len = descriptions.length; i < len; i++) {
                this.destination.DescriptionTranslations.push(JsonNavigator.walkPath(descriptions[i],
                    TEXT_RESSOURCES_PATH_TO_NAME_TRANSLATION_TEXT));
            }

        this.destination.IsHidden = this.destination.Name.indexOf(this.destination.hiddenPrefix) == 0;

        this.destination.Id = JsonNavigator.walkPath(this.buildingData, DESTINATION_FROM_ROOT_PATH_TO_ID);

        var hiddenTopicsId = JsonNavigator.walkPath(this.buildingData, DESTINATION_FROM_ROOT_PATH_TO_HIDDEN_TOPICS);
        if (hiddenTopicsId != null) {
            for (i = 0, len = hiddenTopicsId.length; i < len; i++) {
                // initialize the entry to null
                this.destination.HiddenTopics[hiddenTopicsId[i]] = null;
            }
        }

        var topicsId = JsonNavigator.walkPath(this.buildingData, DESTINATION_FROM_ROOT_PATH_TO_TOPICS);
        if (topicsId) {
            for (i = 0, len = topicsId.length; i < len; i++) {
                // initialize the entry to null
                this.destination.Topics[topicsId[i]] = null;
            }
        }
        var listOfCategories = JsonNavigator.walkPath(this.buildingData, DESTINATION_FROM_ROOT_PATH_TO_CATEGORIES);

        if (listOfCategories) {
            for (i = 0, len = listOfCategories.length; i < len; i++) {
                // initialize the entry to null
                this.destination.Categories[listOfCategories[i]] = null;
            }
        }

        var shapeArray = JsonNavigator.walkPath(this.buildingData, DESTINATION_FROM_ROOT_PATH_TO_ACTUAL_SHAPE_LIST);
        if (shapeArray) {
            for (i = 0, len = shapeArray.length; i < len; i++) {
                var shapeId = JsonNavigator.walkPath(shapeArray[i], DESTINATION_PATH_TO_ACTUAL_SHAPE_LIST_ID);
                // initialize the entry to null
                this.destination.ShapeList[shapeId] = null;
            }
        }

        var externalDatasArray = JsonNavigator.walkPath(this.destinationJson, DESTINATION_PATH_TO_EXTERNAL_DATAS);

        if (!externalDatasArray)
            return;

        this.destination.AdditionalInfos = new Object();

        var datas = JsonNavigator.walkPath(externalDatasArray, DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA);

        if (!datas)
            return;

        for (i = 0, len = datas.length; i < len; i++) {
            var key = JsonNavigator.walkPath(datas[i], DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_KEY);

            var valueUnd = JsonNavigator.walkPath(datas[i], DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_VALUE_UND);

            if (valueUnd) {
                this.destination.AdditionalInfos[key] = valueUnd;
            } else {
                this.destination.AdditionalInfos[key] = new Array();
                this.destination.AdditionalInfos[key][0] = JsonNavigator.walkPath(datas[i],
                    DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_VALUE_EN);
                this.destination.AdditionalInfos[key][1] = JsonNavigator.walkPath(datas[i],
                    DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_VALUE_RU);
            }
        }
    };
    /**
     * @constructor
     * @param {PathPoint}
     *            pPoint
     * @param {ViadirectMap}
     *            pMap
     */
    function InitializePathPointInstance(pPathPoint, pFloor, pMap) {
        this.pathPoint = pPathPoint;

        this.floor = pFloor;

        this.map = pMap;
    }

    /**
     * @type Destination
     */
    InitializePathPointInstance.prototype.pathPoint = new PathPoint();

    /**
     * @type ViadirectMap
     */
    InitializePathPointInstance.prototype.map = new ViadirectMap();

    /**
     *
     */
    InitializePathPointInstance.prototype.execute = function() {
        var position = JsonNavigator.walkPath(this.pathPoint, PATHPOINTS_PATH_POSITION);

        var point = new Point(position.X, position.Z, position.Y);

        var pathPoint = new PathPoint();

        pathPoint.Position = CanvasHelper.DimensionsConverter(point, this.floor.UpperBound, this.floor.LowerBound, this.floor.TextureHeight, this.floor.TextureWidth);

        pathPoint.DestinationReference = JsonNavigator.walkPath(this.pathPoint, PATHPOINTS_PATH_DESTINATION_REFERENCE);

        pathPoint.Name = JsonNavigator.walkPath(this.pathPoint, PATHPOINTS_PATH_NAME);

        pathPoint.Id = JsonNavigator.walkPath(this.pathPoint, PATHPOINTS_PATH_ID);

        pathPoint.Floor = this.floor;

        var dest;
        for (var i = 0, len = pathPoint.DestinationReference.length; i < len; i++) {
            dest = this.map.FindDestinationById(pathPoint.DestinationReference[i]);
            if (dest) {
                dest.PathPoints.push(pathPoint);
            }
        }


        this.map.PathPointsList.push(pathPoint);
    };
    /**
     * @constructor
     * @param {PathPoint}
     *            pPoint
     * @param {ViadirectMap}
     *            pMap
     */
    function InitializePathInstance(pPath, pMap) {
        this.path = pPath;

        this.map = pMap;
    }

    /**
     * @type Destination
     */
    InitializePathInstance.prototype.path = new Path();

    /**
     * @type ViadirectMap
     */
    InitializePathInstance.prototype.map = new ViadirectMap();

    /**
     *
     */
    InitializePathInstance.prototype.execute = function() {
        var path = new Path();

        path.StartingPoint = JsonNavigator.walkPath(this.path, PATH_PATH_STARTING_POINT);

        path.EndingPoint = JsonNavigator.walkPath(this.path, PATH_PATH_ENDING_POINT);

        path.AccesibleToDisabledPeople = JsonNavigator.walkPath(this.path, PATH_PATH_ACESSIBLE_DISABLE);

        path.Id = JsonNavigator.walkPath(this.path, PATH_PATH_ID);

        path.PathDirection = JsonNavigator.walkPath(this.path, PATH_PATH_DIRECTION);

        path.UsableFromDay = JsonNavigator.walkPath(this.path, PATH_PATH_FROM_DAY);

        path.UsableFromHour = JsonNavigator.walkPath(this.path, PATH_PATH_FROM_HOUR);

        path.UsableTillDay = JsonNavigator.walkPath(this.path, PATH_PATH_TILL_DAY);

        path.UsableTillHour = JsonNavigator.walkPath(this.path, PATH_PATH_TILL_HOUR);

        if (path.UsableFromDay === 0 &&
            path.UsableFromHour === 0 &&
            path.UsableTillDay === 0 &&
            path.UsableTillHour === 0)
            path.IsPMROnly = true;

        this.map.PathList.push(path);
    };
    /**
     * @constructor
     */
    function InitializeShapeInstance(pShape, pFloorRef, pShapeJson) {
        this.shape = pShape;
        this.floor = pFloorRef;
        this.shapeJson = pShapeJson;
    }

    /**
     * @type Shape
     */
    InitializeShapeInstance.prototype.shape = new Shape();

    /**
     * @type Floor
     */
    InitializeShapeInstance.prototype.floor = new Floor();

    /**
     * @type Object
     */
    InitializeShapeInstance.prototype.shapeJson = {};

    /**
     *
     */
    InitializeShapeInstance.prototype.execute = function() {

        var shapePackedColor = JsonNavigator.walkPath(this.shapeJson, FLOOR_PATH_TO_SHAPE_COLOR_PACKED_VALUE);
        this.shape.FloorName = this.floor.Name;
        this.shape.Color = CanvasHelper.convertPackedColorToColor(shapePackedColor);
        this.shape.Id = JsonNavigator.walkPath(this.shapeJson, FLOOR_PATH_TO_SHAPE_ID);
        this.shape.Selectable = JsonNavigator.walkPath(this.shapeJson, FLOOR_PATH_TO_SHAPE_SELECTABLE);

        var baselinePointsArray = JsonNavigator.walkPath(this.shapeJson, FLOOR_PATH_TITLE_BASELINE);
        var baselineMaximumTextHeight = JsonNavigator.walkPath(this.shapeJson, FLOOR_PATH_TITLE_BASELINE_MAXHEIGHT);
        if (baselineMaximumTextHeight) {
            var p1 = CanvasHelper.DimensionsConverter(new Point(0, 0), this.floor.UpperBound, this.floor.LowerBound, this.floor.TextureHeight, this.floor.TextureWidth);
            var p2 = CanvasHelper.DimensionsConverter(new Point(baselineMaximumTextHeight, 0), this.floor.UpperBound, this.floor.LowerBound, this.floor.TextureHeight, this.floor.TextureWidth);
            this.shape.TitleBaseline.MaxTextHeight = (p2.X - p1.X);
        }

        if (baselinePointsArray) {
            for (var i = 0, len = baselinePointsArray.length; i < len; i++) {
                var newPoint = new Point();

                if (this.floor.Texture == null) {
                    newPoint.X = baselinePointsArray[i].X;
                    newPoint.Y = baselinePointsArray[i].Y;
                } else {
                    newPoint = CanvasHelper.DimensionsConverter(baselinePointsArray[i], this.floor.UpperBound, this.floor.LowerBound,
                        this.floor.TextureHeight, this.floor.TextureWidth);
                }

                this.shape.TitleBaseline.PointList.push(newPoint);
            }
        }

        var centerPoint = JsonNavigator.walkPath(this.shapeJson, FLOOR_PATH_CENTER_POINT);
        var centerPointNewCoord = new Point(centerPoint.X, centerPoint.Z);
        this.shape.CenterPoint = CanvasHelper.DimensionsConverter(centerPointNewCoord, this.floor.UpperBound, this.floor.LowerBound,
            this.floor.TextureHeight, this.floor.TextureWidth);

        var points = JsonNavigator.walkPath(this.shapeJson, FLOOR_PATH_TO_SHAPE_POINTS);

        for (var i = 0, len = points.length; i < len; i++) {
            var newPoint = new Point();

            if (this.floor.Texture == null) {
                newPoint.X = points[i].X;
                newPoint.Y = points[i].Y;
            } else {
                newPoint = CanvasHelper.DimensionsConverter(points[i], this.floor.UpperBound, this.floor.LowerBound,
                    this.floor.TextureHeight, this.floor.TextureWidth);
            }

            this.shape.PointList.push(newPoint);
        }
    };

    /**
     * @fileOverview
     *
     * @require viadirect/commands/InitializeShapeInstance.js
     */

    /**
     * @constructor
     * @param {Floor}
     *                floor instance
     * @param {MapLoader}
     *                map datas
     */
    function InitializeFloorInstance(pFloor, jsonData, pMap) {
        this.floor = pFloor;
        this.jsonData = jsonData;
        this.map = pMap;
    }

    /**
     * @type ViadirectMap
     */
    InitializeFloorInstance.prototype.map = new ViadirectMap();

    /**
     * @type Floor
     */
    InitializeFloorInstance.prototype.floor = new Floor;

    /**
     *
     */
    InitializeFloorInstance.prototype.execute = function() {

        var translations = JsonNavigator.walkPath(this.jsonData, FLOOR_PATH_TO_NAME_TRANSLATIONS);

        var shapesArray = JsonNavigator.walkPath(this.jsonData, FLOOR_PATH_TO_SHAPES);

        var i, len;

        for (i = 0, len = translations.length; i < len; i++) {
            this.floor.NameTranslations.push(JsonNavigator.walkPath(translations[i], TEXT_RESSOURCES_PATH_TO_NAME_TRANSLATION_TEXT));
        }

        this.floor.Color = CanvasHelper.convertPackedColorToColor(JsonNavigator.walkPath(this.jsonData, FLOOR_PATH_TO_PACKED_COLOR));

        if (this.jsonData.FloorTextureImage)
            this.floor.Texture = this.jsonData.FloorTextureImage;

        var ub = JsonNavigator.walkPath(this.jsonData, FLOOR_UPPER_POINT_PATH),
            lb = JsonNavigator.walkPath(this.jsonData, FLOOR_LOWER_POINT_PATH),
            pos = JsonNavigator.walkPath(this.jsonData, FLOOR_POSITION_PATH);

        this.floor.UpperBound = new Point(ub.X, ub.Y, ub.Z);

        this.floor.LowerBound = new Point(lb.X, lb.Y, lb.Z);

        this.floor.Position = new Point(pos.X, pos.Y, pos.Z);

        var width = JsonNavigator.walkPath(this.jsonData, FLOOR_TEXTURE_WIDTH);
        var height = JsonNavigator.walkPath(this.jsonData, FLOOR_TEXTURE_HEIGHT);

        if (typeof(width) == "string") {
            width = parseInt(width);
            this.floor.TextureWidth = width;
        }

        if (typeof(height) == "string") {
            height = parseInt(height);
            this.floor.TextureHeight = height;
        }

        var shapeVo;

        for (i = 0, len = shapesArray.length; i < len; i++) {
            shapeVo = new Shape();
            new InitializeShapeInstance(shapeVo, this.floor, shapesArray[i]).execute();
            this.floor.AddShape(shapeVo);
            this.map.ShapeList[shapeVo.Id] = shapeVo;
        }
    };
    /**
     *
     * @file viadirect/io/MapLoader.js
     * @require viadirect/io/LoadingQueue.js
     * @require viadirect/commands/InitializeCategoryInstance.js
     * @require viadirect/commands/InitializeTopicInstance.js
     * @require viadirect/commands/InitializeDestinationInstance.js
     * @require viadirect/commands/InitializePathPointInstance.js
     * @require viadirect/commands/InitializePathInstance.js
     * @require viadirect/commands/InitializeFloorInstance.js
     */

    /**
     * @constructor
     *
     * @param {ViaDirectMap}
     *                map model to feed
     * @param {EventManagerDecorator}
     *                event manager
     */
    function MapLoader(pMapModel, pLoadingQueue, pEventManager) {
        if (arguments.length) {
            /**
             * @type ViadirectMap
             */
            this.mapModel = pMapModel;
            this.eventManager = pEventManager;
            this._loadingQueue = pLoadingQueue;
            this.$_datasLoaded = $.proxy(this.datasLoaded, this);
        }
    };

    /**
     * starts the loading process
     *
     * @param
     */
    MapLoader.prototype.start = function(pModel) {
        this._loadingQueue.unbind(LoadingQueue.QUEUE_FINISHED, this.$_datasLoaded).bind(LoadingQueue.QUEUE_FINISHED,
            this.$_datasLoaded);
        if (pModel != null) {
            this.setModel(pModel, this._loadingQueue);
        } else {
            // ajax json loading
            this._loadingQueue.add(this.pathManager.getBuildingUrl(this.params), null, $.proxy(this.buildingLoaded, this));
            this._loadingQueue.start();
        }

        ;
    };

    /**
     * callback invoked when the
     *
     * @type Function
     */
    MapLoader.prototype.modelReady = null;

    /**
     * @type LoadingQueue
     */
    MapLoader.prototype._loadingQueue = new LoadingQueue;

    /**
     * @type ViadirectMap
     */
    MapLoader.prototype.mapModel = new ViadirectMap();

    /**
     * @type PathManager pathManager
     */
    MapLoader.prototype.pathManager = null;

    /**
     *
     * @param {PathManager}
     *                pPathManagerImpl
     */
    MapLoader.prototype.setPathManager = function(pPathManagerImpl) {
        /**
         *
         * @type PathManager
         */
        this.pathManager = pPathManagerImpl;
    };

    /**
     *
     * @param {Object}
     *                PModel
     * @param {LoadingQueue}
     *                pQueue
     */
    MapLoader.prototype.setModel = function(pModel, pQueue) {

        var i, len, floorName, floor;

        var errorCallback = $.proxy(this.ErrorControler, this);

        for (i = 0, len = pModel.Categories.length; i < len; i++) {
            this.categoryLoaded(pModel.Categories[i], pQueue);
        }

        for (i = 0, len = pModel.Topics.length; i < len; i++) {
            this.topicLoaded(pModel.Topics[i], pQueue);
        }

        for (i = 0, len = pModel.HiddenTopics.length; i < len; i++) {
            this.topicLoaded(pModel.HiddenTopics[i], pQueue);
        }

        for (i = 0, len = pModel.Destinations.length; i < len; i++) {
            this.destinationLoaded(pModel.Destinations[i], pQueue);
        }

        for (i = 0, len = pModel.Floors.length; i < len; i++) {
            floorName = JsonNavigator.walkPath(pModel.Floors[i], FLOOR_PATH_TO_NAME);
            floor = new Floor();
            floor.Name = floorName;

            this.mapModel.AddFloor(floor);
            var floorTextureName = JsonNavigator.walkPath(pModel.Floors[i], FLOOR_PATH_TO_TEXTURE_NAME);
            pQueue.addImage(this.pathManager.getFloorTextureUrl(floor.Name, floorTextureName), $.proxy(this.floorTextureLoaded, this, floor.Name,
                pModel.Floors[i], pModel), errorCallback);

        }

        for (i = 0, len = pModel.Paths.length; i < len; i++) {
            this.pathLoaded(pModel.Paths[i], pQueue);
        }

        if (pQueue.length()) {
            pQueue.start();
        }
    };

    /**
     * when the building file is loaded adding all external urls referenced into
     *
     * @param {Object}
     *                data
     * @param {LoadingQueue}
     *                pQueue
     */
    MapLoader.prototype.buildingLoaded = function(data, pQueue) {
        var i, len, floor;

        var floorsArray = JsonNavigator.walkPath(data, BUILDING_FLOORS_PATH);

        var categoriesArray = JsonNavigator.walkPath(data, BUILDING_PATH_TO_CATEGORIES);

        var topicsArray = JsonNavigator.walkPath(data, BUILDING_PATH_TO_TOPICS);

        var hiddenTopicsArray = JsonNavigator.walkPath(data, BUILDING_PATH_TO_HIDDEN_TOPICS);

        var destinationsArray = JsonNavigator.walkPath(data, BUILDING_PATH_TO_DESTINATIONS);

        var errorCallback = $.proxy(this.ErrorControler, this);
        var destinationVO;
        for (i = 0, len = destinationsArray.length; i < len; i++) {
            var destinationName = JsonNavigator.walkPath(destinationsArray[i], DESTINATION_PATH_TO_ID);
            this.destinationLoaded(destinationsArray[i], pQueue);
            // pQueue.add( this.pathManager.getDestinationUrl(destinationName),
            // null, $.proxy( this.destinationLoaded, this ), errorCallback );
        }

        for (i = 0, len = floorsArray.length; i < len; i++) {
            floorName = JsonNavigator.walkPath(floorsArray[i], FLOOR_PATH_TO_NAME);
            floor = new Floor();
            floor.Name = floorName;
            this.mapModel.AddFloor(floor);
            pQueue.addImage(this.pathManager.getFloorTextureUrl(floorName), $.proxy(this.floorTextureLoaded, this, floorName),
                errorCallback);
            pQueue.add(this.pathManager.getFloorUrl(floorName), null, $.proxy(this.floorLoaded, this), errorCallback);
        }

        for (i = 0, len = categoriesArray.length; i < len; i++) {
            pQueue.add(this.pathManager.getCategoryUrl(categoriesArray[i]), null, $.proxy(this.categoryLoaded, this), errorCallback);
        }

        for (i = 0, len = categoriesArray.length; i < len; i++) {
            pQueue.add(this.pathManager.getCategoryUrl(categoriesArray[i]), null, $.proxy(this.categoryLoaded, this), errorCallback);
        }

        for (i = 0, len = topicsArray.length; i < len; i++) {
            pQueue.add(this.pathManager.getTopicUrl(topicsArray[i]), null, $.proxy(this.topicLoaded, this), errorCallback);
        }

        for (i = 0, len = hiddenTopicsArray.length; i < len; i++) {
            pQueue.add(this.pathManager.getHiddenTopicUrl(hiddenTopicsArray[i]), null, $.proxy(this.hiddenTopicLoaded, this),
                errorCallback);
        }
    };

    /**
     *
     * @param {Object}
     *                pData
     * @param {LoadingQueue}
     *                pQueue
     */
    MapLoader.prototype.destinationLoaded = function(pData, pQueue) {
        var destinationVO = new Destination();
        new InitializeDestinationInstance(destinationVO, pData, this.mapModel).execute();
        this.mapModel.DestinationList[destinationVO.Id] = destinationVO;
    };

    /**
     *
     * @param {Object}
     *                pData
     * @param {LoadingQueue}
     *                pQueue
     */
    MapLoader.prototype.destinationLogoLoaded = function(pData, pQueue) {
        var destinationVO = new Destination();
        new InitializeDestinationInstance(destinationVO, destinationsArray[i], this.mapModel).execute();
        this.mapModel.DestinationList[destinationVO.Id] = destinationVO;
    };

    /**
     *
     * @param {Object}
     *                pData
     * @param {LoadingQueue}
     *                pQueue
     */
    MapLoader.prototype.floorLoaded = function(pData, pQueue, pModel) {
        var floorName = JsonNavigator.walkPath(pData, FLOOR_PATH_TO_NAME);
        var floor = this.mapModel.FindFloorByName(floorName);
        new InitializeFloorInstance(floor, pData, this.mapModel).execute();
        this.LoadPathPoints(pModel, this.mapModel.FindFloorByName(floorName));
        var errorCallback = $.proxy(this.ErrorControler, this);
    };

    MapLoader.prototype.pathLoaded = function(pPath, pQueue) {
        new InitializePathInstance(pPath, this.mapModel).execute();
    };

    MapLoader.prototype.LoadPathPoints = function(pModel, pFloor) {
        for (var i = 0; i < pModel.Points.length; i++) {
            if (JsonNavigator.walkPath(pModel.Points[i], PATHPOINTS_PATH_POSITION).Y == pFloor.Position.Y) {
                new InitializePathPointInstance(pModel.Points[i], pFloor, this.mapModel).execute();
            }
        }
    };

    /**
     *
     * @param {HTMLImageElement}
     *                pImg
     * @param {LoadingQueue}
     *                pQueue
     */
    MapLoader.prototype.floorTextureLoaded = function(pFloorName, pFloorData, pModel, pImg, pQueue) {
        var floor = this.mapModel.FindFloorByName(pFloorName);

        if (floor) {
            floor.Texture = pImg;
            if (!floor.TextureWidth && !floor.TextureHeight) {
                floor.TextureWidth = floor.Texture.width;
                floor.TextureHeight = floor.Texture.height;
            }
            this.floorLoaded(pFloorData, pQueue, pModel);
        }
    };

    MapLoader.prototype.arrowLoaded = function(pImg, pQueue) {
        this.mapModel.Arrow = pImg;
    };

    /**
     *
     * @param {Object}
     *                pData
     * @param {LoadingQueue}
     *                pQueue
     */
    MapLoader.prototype.categoryLoaded = function(pData, pQueue) {
        var category = new Category();
        new InitializeCategoryInstance(category, pData).execute();
        this.mapModel.CategoriesList[category.Id] = category;
    };

    /**
     *
     * @param {Object}
     *                pData
     * @param {LoadingQueue}
     *                pQueue
     */
    MapLoader.prototype.topicLoaded = function(pData, pQueue) {
        var topic = new Topic();
        new InitializeTopicInstance(topic, pData).execute();
        this.mapModel.TopicList[topic.Id] = topic;
    };

    /**
     *
     * @param {Object}
     *                pData
     * @param {LoadingQueue}
     *                pQueue
     */
    MapLoader.prototype.hiddenTopicLoaded = function(pData, pQueue) {
        var topic = new Topic();
        new InitializeTopicInstance(topic, pData).execute();
        this.mapModel.HiddenTopicList[topic.Id] = topic;
    };

    /**
     *
     * @param {jQuery.Event}
     *                ev
     * @param {LoadingQueue}
     *                pQueue
     */
    MapLoader.prototype.datasLoaded = function(ev, pQueue) {

        this._loadingQueue.unbind(LoadingQueue.QUEUE_FINISHED, this.$_datasLoaded);
        if (this.modelReady !== null) {
            this.modelReady(this.mapModel);
        }

    };

    MapLoader.prototype.ErrorControler = function(pData, pQueue) {
        this.eventManager.trigger('ErrorLoadingMap');
    };


    MapLoader.prototype.destinationsInfosLoaded = function(pData) {
        this.eventManager.trigger('destinationLoaded', pData);
    };

    MapLoader.prototype.handleLoadResource = function(resourceKey, cssSelector) {
        //    console.log(resourceKey + " : " + cssSelector);
        if (this.mapModel.ResourcesMap.hasResource(resourceKey)) {
            var o = CSSHelper.FindCssRuleBySelector(cssSelector);

            var url;
            if (o && 'style' in o && 'backgroundImage' in o.style) {
                //            console.log( o.style.backgroundImage);
                url = o.style.backgroundImage.replace(/url\(\s*(?:"|'|)([^"']*)(?:"|'|)\s*\)/, "$1");

                if (url.indexOf("./") == 0) {
                    var refUrl = o.parentStyleSheet !== null && o.parentStyleSheet.href ? o.parentStyleSheet.href : location.toString();
                    var a = $('<a>', {
                        href: refUrl
                    }).get(0);
                    var p = a.pathname.split("/").slice(0, -1).join("/");
                    if (p.indexOf("/") !== 0) p = "/" + p;
                    url = url.replace(/^\.\/(.*)$/, a.protocol + "//" + a.hostname + p + "/$1");
                }
                //            console.log( o.parentStyleSheet );
                this._loadingQueue.addImage(url, $.proxy(this.resourceLoaded, this, resourceKey));
            }
        }
    };

    MapLoader.prototype.resourceLoaded = function(resourceKey, pImage, pQueue) {
        this.mapModel.ResourcesMap[resourceKey] = pImage;
    };
    /**
     * @fileOverview
     *
     *
     * @require viadirect/model/ViadirectMap.js
     * @require viadirect/utils/StylesManager.js
     */

    /**
     *
     * @param {ViadirectMap} map
     * @param {StylesManager} styleManager
     * @param {Object} colorMap
     * @param {Number} languageId
     */
    function InitializeVDMap(map, styleManager, colorMap, languageId) {
        this.map = map;
        this.stylesManager = styleManager;
        this.colorMap = colorMap;
        this.currentLanguageId = languageId;
    };

    /**
     * @type ViadirectMap
     */
    InitializeVDMap.prototype.map = new ViadirectMap;

    /**
     * @type StylesManager
     */
    InitializeVDMap.prototype.stylesManager = new StylesManager();

    /**
     * @type Number
     */
    InitializeVDMap.prototype.currentLanguageId = 0;

    /**
     * @type Object
     */
    InitializeVDMap.prototype.colorMap = {};

    /**
     *
     * @param mapLoaderObject
     */
    InitializeVDMap.prototype.execute = function() {
        var i, len, k, lenk, currentDestination, current;
        // initialize title baseline default styles
        var defaultTitleBaselineStyle = this.stylesManager.lookupStyle(this.stylesManager.getTitleBaselineStyleName(StylesManager.STATE_DEFAULT));
        if (defaultTitleBaselineStyle && ("maxHeight" in defaultTitleBaselineStyle)) defaultTitleBaselineStyle.maxFontSize = defaultTitleBaselineStyle.maxHeight;
        var defaultTitleBaselineStyleActive = $.extend(true, {}, defaultTitleBaselineStyle, this.stylesManager.lookupStyle(this.stylesManager.getTitleBaselineStyleName(StylesManager.STATE_ACTIVE)));
        var defaultTitleBaselineStyleHover = $.extend(true, {}, defaultTitleBaselineStyle, this.stylesManager.lookupStyle(this.stylesManager.getTitleBaselineStyleName(StylesManager.STATE_HOVER)));


        this.stylesManager.setStyle(this.stylesManager.getTitleBaselineStyleName(StylesManager.STATE_DEFAULT), defaultTitleBaselineStyle);
        this.stylesManager.setStyle(this.stylesManager.getTitleBaselineStyleName(StylesManager.STATE_ACTIVE), defaultTitleBaselineStyleActive);
        this.stylesManager.setStyle(this.stylesManager.getTitleBaselineStyleName(StylesManager.STATE_HOVER), defaultTitleBaselineStyleHover);

        // initialize shapes default styles
        var defaultShapeStyle = this.stylesManager.lookupStyle(this.stylesManager.getShapeStyleName(StylesManager.STATE_DEFAULT));
        var defaultShapeStyleActive = $.extend(true, {}, defaultShapeStyle, this.stylesManager.lookupStyle(this.stylesManager.getShapeStyleName(StylesManager.STATE_ACTIVE)));
        var defaultShapeStyleHover = $.extend(true, {}, defaultShapeStyle, this.stylesManager.lookupStyle(this.stylesManager.getShapeStyleName(StylesManager.STATE_HOVER)));

        var defaultDestinationStyle = $.extend(true, {}, defaultShapeStyle, this.stylesManager.lookupStyle(this.stylesManager.getDefaultDestinationStyleName(StylesManager.STATE_DEFAULT)));
        var defaultDestinationStyleActive = $.extend(true, {}, defaultDestinationStyle, this.stylesManager.lookupStyle(this.stylesManager.getDefaultDestinationStyleName(StylesManager.STATE_ACTIVE)));
        var defaultDestinationStyleHover = $.extend(true, {}, defaultDestinationStyle, this.stylesManager.lookupStyle(this.stylesManager.getDefaultDestinationStyleName(StylesManager.STATE_HOVER)));

        this.stylesManager.setStyle(this.stylesManager.getShapeStyleName(StylesManager.STATE_DEFAULT), defaultShapeStyle);
        this.stylesManager.setStyle(this.stylesManager.getShapeStyleName(StylesManager.STATE_ACTIVE), defaultShapeStyleActive);
        this.stylesManager.setStyle(this.stylesManager.getShapeStyleName(StylesManager.STATE_HOVER), defaultShapeStyleHover);

        this.stylesManager.setStyle(this.stylesManager.getDefaultDestinationStyleName(StylesManager.STATE_DEFAULT), defaultDestinationStyle);
        this.stylesManager.setStyle(this.stylesManager.getDefaultDestinationStyleName(StylesManager.STATE_ACTIVE), defaultDestinationStyleActive);
        this.stylesManager.setStyle(this.stylesManager.getDefaultDestinationStyleName(StylesManager.STATE_HOVER), defaultDestinationStyleHover);

        if (this.colorMap) {
            if ("selected" in this.colorMap && this.colorMap.selected) defaultShapeStyleActive.backgroundColor = this.colorMap.selected;
            if ("over" in this.colorMap && this.colorMap.over) defaultShapeStyleHover.backgroundColor = this.colorMap.over;
            if ("titleSelected" in this.colorMap && this.colorMap.titleSelected) defaultTitleBaselineStyleActive.color = this.colorMap.titleSelected;
            if ("titleOver" in this.colorMap && this.colorMap.titleOver) defaultTitleBaselineStyleHover.color = this.colorMap.titleOver;
        }

        var destinationStyle, destinationStyleActive, destinationStyleHover;

        for (i = 0, len = this.map.PathList.length; i < len; i++) {
            this.map.PathList[i].StartingPoint = this.map.FindPathPointById(this.map.PathList[i].StartingPoint);
            this.map.PathList[i].EndingPoint = this.map.FindPathPointById(this.map.PathList[i].EndingPoint);
        }

        for (i = 0, len = this.map.DestinationList.length; i < len; i++) {
            /** @type Destination currentDestination */
            currentDestination = this.map.DestinationList[i];

            if (!currentDestination) {
                continue;
            }


            for (k = 0, lenk = this.map.HiddenTopicList.length, current; k < lenk; k++) {
                current = this.map.HiddenTopicList[k];

                if (current && current.Id in currentDestination.HiddenTopics)
                    currentDestination.HiddenTopics[current.Id] = current;
            }

            for (k = 0, lenk = this.map.TopicList.length, current; k < lenk; k++) {
                current = this.map.TopicList[k];
                if (current && current.Id in currentDestination.Topics)
                    currentDestination.Topics[current.Id] = current;
            }

            for (k = 0, lenk = this.map.CategoriesList.length, current; k < lenk; k++) {
                current = this.map.CategoriesList[k];
                if (current && current.Id in currentDestination.Categories) {
                    currentDestination.Categories[current.Id] = current;
                    current.DestinationsList.push(currentDestination);
                }
            }




            for (k = 0, lenk = this.map.ShapeList.length, current; k < lenk; k++) {
                /** @var {Shape} current */
                current = this.map.ShapeList[k];
                if (current && current.Id in currentDestination.ShapeList) {
                    current.Selectable = !currentDestination.IsHidden;
                    current.DestinationsList.push(currentDestination.Id);
                    currentDestination.ShapeList[current.Id] = current;
                    if (!currentDestination.IsHidden) {
                        current.TitleBaseline.Label = currentDestination.NameTranslations[this.currentLanguageId];

                    } else {
                        if (this.testDestinationList(current)) {
                            // we don't need baselines for hidden destinations ...
                            current.TitleBaseline = null;
                        }
                    }
                }
            }

            for (shape in currentDestination.ShapeList) {
                if (currentDestination.ShapeList[shape] === null)
                    delete currentDestination.ShapeList[shape];
            }

            // resets indexes
            currentDestination.HiddenTopics = currentDestination.HiddenTopics.filter(this._dummyFilter);
            currentDestination.Topics = currentDestination.Topics.filter(this._dummyFilter);
            currentDestination.Categories = currentDestination.Categories.filter(this._dummyFilter);
            currentDestination.ShapeList = currentDestination.ShapeList.filter(this._dummyFilter);

            this._initializeDestinationShapeStyle(currentDestination, defaultDestinationStyle, defaultDestinationStyleActive, defaultDestinationStyleHover);

            this._initializeDestinationTitleBaselineStyle(currentDestination, defaultTitleBaselineStyle, defaultTitleBaselineStyleActive, defaultTitleBaselineStyleHover);
        }
    };

    /**
     * @param {Shape} shape
     * @returns {Boolean}
     */
    InitializeVDMap.prototype.testDestinationList = function(shape) {
        var i, len, dest, result = true;
        for (i = 0, len = shape.DestinationsList.length; i < len; i++) {
            dest = this.map.FindDestinationById(shape.DestinationsList[i]);
            result = result && dest.isHidden;
            if (!result) break;
        }
        return result;
    };

    /**
     * lookup for shape style overrides specific per destination
     * if found styles are stored into the stylesManager
     *
     * @param {Destination} target destination
     * @param {CssProperties} defaultShape Style
     * @param {CssProperties} defaultShape active Style (when selected)
     * @param {CssProperties} defaultShape hover Style (when mouseover)
     */
    InitializeVDMap.prototype._initializeDestinationShapeStyle = function(currentDestination, defaultShapeStyle, defaultShapeStyleActive, defaultShapeStyleHover) {
        var destinationStyle, destinationStyleActive, destinationStyleHover;
        // handling style overriding per destination 
        destinationStyle = this.stylesManager.lookupStyle(this.stylesManager.getStyleNameForDestination(currentDestination.Id, StylesManager.STATE_DEFAULT));
        destinationStyleActive = this.stylesManager.lookupStyle(this.stylesManager.getStyleNameForDestination(currentDestination.Id, StylesManager.STATE_ACTIVE));
        destinationStyleHover = this.stylesManager.lookupStyle(this.stylesManager.getStyleNameForDestination(currentDestination.Id, StylesManager.STATE_HOVER));

        if (destinationStyle) {
            destinationStyle = $.extend({}, defaultShapeStyle, destinationStyle);
            if (currentDestination.ShapeList.length && currentDestination.ShapeList[0].Color != null) {
                destinationStyle.backgroundColor = currentDestination.ShapeList[0].Color;
            }
            this.stylesManager.setStyle(this.stylesManager.getStyleNameForDestination(currentDestination.Id, StylesManager.STATE_DEFAULT), destinationStyle);
        }

        if (destinationStyleActive) {
            destinationStyleActive = $.extend({}, defaultShapeStyleActive, destinationStyleActive);
            this.stylesManager.setStyle(this.stylesManager.getStyleNameForDestination(currentDestination.Id, StylesManager.STATE_ACTIVE), destinationStyleActive);
        }

        if (destinationStyleHover) {
            destinationStyleHover = $.extend({}, defaultShapeStyleHover, destinationStyleHover);
            this.stylesManager.setStyle(this.stylesManager.getStyleNameForDestination(currentDestination.Id, StylesManager.STATE_HOVER), destinationStyleHover);
        }
    };

    /**
     * lookup for titlebaseline style overrides specific per destination
     * if found styles are stored into the stylesManager
     *
     * @param {Destination} target destination
     * @param {CssProperties} default Style for title baseline
     * @param {CssProperties} default Style for title baseline active  (when selected)
     * @param {CssProperties} defaultShape Style for title baseline hover (when mouseover)
     */
    InitializeVDMap.prototype._initializeDestinationTitleBaselineStyle = function(currentDestination, defaultTitleBaselineStyle, defaultTitleBaselineStyleActive, defaultTitleBaselineStyleHover) {
        var destinationBaselineStyle, destinationBaselineStyleActive, destinationBaselineStyleHover;

        // handling style overriding per destination 
        destinationBaselineStyle = this.stylesManager.lookupStyle(this.stylesManager.getDestinationTitleBaselineStyleName(currentDestination.Id, StylesManager.STATE_DEFAULT));
        destinationStyleActive = this.stylesManager.lookupStyle(this.stylesManager.getDestinationTitleBaselineStyleName(currentDestination.Id, StylesManager.STATE_ACTIVE));
        destinationStyleHover = this.stylesManager.lookupStyle(this.stylesManager.getDestinationTitleBaselineStyleName(currentDestination.Id, StylesManager.STATE_HOVER));

        if (destinationBaselineStyle) {
            destinationBaselineStyle = $.extend({}, defaultTitleBaselineStyle, destinationBaselineStyle);
            this.stylesManager.setStyle(this.stylesManager.getDestinationTitleBaselineStyleName(currentDestination.Id, StylesManager.STATE_DEFAULT), destinationBaselineStyle);
        }

        if (destinationBaselineStyleActive) {
            destinationBaselineStyleActive = $.extend({}, defaultTitleBaselineStyleActive, destinationBaselineStyleActive);
            this.stylesManager.setStyle(this.stylesManager.getDestinationTitleBaselineStyleName(currentDestination.Id, StylesManager.STATE_ACTIVE), destinationBaselineStyleActive);
        }

        if (destinationBaselineStyleHover) {
            destinationBaselineStyleHover = $.extend({}, defaultTitleBaselineStyleHover, destinationBaselineStyleHover);
            this.stylesManager.setStyle(this.stylesManager.getDestinationTitleBaselineStyleName(currentDestination.Id, StylesManager.STATE_HOVER), destinationBaselineStyleHover);
        }

    };

    InitializeVDMap.prototype._dummyFilter = function(ele) {
        return ele !== undefined && ele !== null;
    };

    /*
     * @class CSSHelper
     * @namespace utils
     */
    CSSHelper = {};

    CSSHelper._CacheMap = {};

    /**
     * @desc Converts a color from a packed int XNA color to a Color Object
     * @param {number}
     *                packedColor - The color packed in the c# XNA standard
     * @returns {Color}
     */
    CSSHelper.FindCssRuleBySelector = function(selector) {
        var results = [],
            candidate, i, j, len, lenj, sheet;
        if (!(selector in this._CacheMap)) {
            for (i = 0, len = document.styleSheets.length; i < len; i++) {
                try {
                    sheet = document.styleSheets[i];
                    if ('cssRules' in sheet && sheet.cssRules) {
                        candidate = this._FindCssRuleBySelector(selector, sheet, sheet.cssRules);
                        if (candidate) results = results.concat(candidate);
                    } else if ('imports' in sheet && sheet.imports) {
                        for (j = 0, lenj = sheet.imports.length; j < lenj; j++) {
                            candidate = this._FindCssRuleBySelector(selector, sheet, sheet.imports[j].rules);
                            if (candidate) results = results.concat(candidate);
                        }
                    }
                } catch (e) {
                    if (e.name !== 'SecurityError') {
                        // firefox may throw a SecurityError exception when css origin is different 
                        // from our domain
                        throw e;
                    }
                }
            }
            var rule = {
                style: {}
            };
            for (i = 0, len = results.length; i < len; i++) {
                rule = $.extend(true, {}, rule, results[i]);
            };
            this._CacheMap[selector] = rule;
        }
        return this._CacheMap[selector];
    };

    CSSHelper._FindCssRuleBySelector = function(selector, sheet, coll) {
        var candidates = [],
            i, len, j, lenj, sheet, rule;

        if (sheet.cssRules) {
            for (i = 0, len = coll.length; i < len; i++) {
                rule = coll[i]
                if ('selectorText' in rule && rule.selectorText === selector) {
                    candidates.push(rule);
                } else if ('styleSheet' in rule && rule.styleSheet) {
                    if (rule.styleSheet) {
                        candidates = candidates.concat(CSSHelper._FindCssRuleBySelector(selector, rule.styleSheet, rule.styleSheet.cssRules));
                    } else if ('imports' in rule.styleSheet) {
                        for (j = 0, lenj = rule.styleSheet.imports.length; j < lenj; j++) {
                            candidates = candidates.concat(this._FindCssRuleBySelector(selector, rule.styleSheet, rule.styleSheet.imports[j].rules));
                            //if( candidate ) break;
                        }
                    }
                }
                //if( candidate ) break;
            }
        }
        return candidates;
    };

    CSSHelper.CleanCSSObject = function(cssStyleList) {
        var result = new Array();

        for (var key in cssStyleList)
            if (cssStyleList[key])
                result[key] = cssStyleList[key];

        return result;
    };
    /**
     *
     * @file VDCanvasViewerBootstrap.js
     *
     * @require viadirect/utils/EventManagerDecorator.js
     * @require viadirect/io/PathManager.js
     * @require viadirect/utils/CanvasHelper.js
     * @require viadirect/utils/JsonNavigator.js
     * @require viadirect/utils/PathCalculator.js
     * @require viadirect/model/ViadirectMap.js
     * @require viadirect/view/MapManipulator.js
     * @require viadirect/view/CanvasManager.js
     * @require viadirect/io/LoadingQueue.js
     * @require viadirect/io/MapLoader.js
     * @require viadirect/commands/InitializeVDMap.js
     * @require viadirect/utils/CSSHelper.js
     * @require viadirect/utils/StylesManager.js
     *
     */

    /**
     * @constructor
     * @memberof {viadirect.canvas.viewer}
     */
    function VDCanvasViewerBootstrap(pParams) {
        this.params = pParams;
    };

    VDCanvasViewerBootstrap.prototype.params = null;

    /**
     * @type {PathManager}
     */
    VDCanvasViewerBootstrap.prototype._pathManager = new PathManager();

    /**
     * @type {UserInterface}
     */
    VDCanvasViewerBootstrap.prototype._userIntefrace = null;

    /**
     * @type {MapManipulator}
     */
    VDCanvasViewerBootstrap.prototype._mapManipulator = null;

    /**
     * @type {EventManagerDecorator} responsible to dispatch events
     */
    VDCanvasViewerBootstrap.prototype._eventManager = null;

    /**
     * @type {CanvasManager}
     */
    VDCanvasViewerBootstrap.prototype._canvas = null;

    VDCanvasViewerBootstrap.prototype.getEventManager = function() {
        return this._eventManager;
    };

    /**
     * @type {LoadingQueue}
     */
    VDCanvasViewerBootstrap.prototype.getLoadingQueue = function() {
        return this._loadingQueue;
    };

    /**
     *
     * @param {PathManager}
     *                pPathManager the path manager
     */
    VDCanvasViewerBootstrap.prototype.setPathManager = function(pPathManager) {
        this._pathManager = pPathManager;
    };

    /**
     *
     * @returns {PathManager}
     */
    VDCanvasViewerBootstrap.prototype.getPathManager = function() {
        return this._pathManager;
    };

    /**
     * setter for eventManager
     *
     * @param {EventManagerDecorator}
     *                pManager
     */
    VDCanvasViewerBootstrap.prototype.setEventManager = function(pManager, pContainer) {
        this._eventManager = new EventManagerDecorator(pManager, pContainer ? pContainer.attr('id') : "");
        this._loadingQueue = new LoadingQueue(this._eventManager);
        this._eventManager.bind('vdRequestItinerary', $.proxy(this.requestItinerary, this));
        this._eventManager.bind('vdSelectDestinations', $.proxy(this.selectDestinations, this));
        this._eventManager.bind('vdSelectDestination', $.proxy(this.selectDestinations, this));
        this._eventManager.bind('vdSetPMRMode', $.proxy(this.setPMRMode, this));
    };

    /**
     *
     */
    VDCanvasViewerBootstrap.DATA_KEY = "vdwebviewer";

    /**
     *
     * @param pUi
     */
    VDCanvasViewerBootstrap.prototype.setUserInterface = function(pUi) {
        if (pUi) {
            pUi.register(this, this._eventManager, this.params.userInterfaceParams);
        }
        this._eventManager.trigger("vdInitializeContainer", [this._container]);
    };

    VDCanvasViewerBootstrap.prototype.setStartingPathPoint = function(pValue) {

        if (pValue) {
            if ("pointId" in pValue && pValue.pointId !== null)
                this._mapManipulator.setStartingPointById(pValue.pointId);
            else if ("destinationId" in pValue && pValue.destinationId !== null)
                this._mapManipulator.setStartingPointByShopId(pValue.destinationId);
            else if ("externalDestinationId" in pValue)
                this._mapManipulator.setStartingPointByExternalShopId(pValue.externalDestinationId);
        } else {
            this._mapManipulator.setStartingPoint(null);
        }
    };

    VDCanvasViewerBootstrap.prototype.setSelectedDestination = function(pSelectedDestination) {

        var destination;
        if (pSelectedDestination && isNaN(pSelectedDestination) && ('id' in pSelectedDestination && 'fieldName' in pSelectedDestination)) {
            destination = this._mapManipulator.FindDestinationByExternalId(pSelectedDestination.id, pSelectedDestination.fieldName);
        } else if (pSelectedDestination && isNaN(pSelectedDestination) && ('id' in pSelectedDestination && 'exernal' in pSelectedDestination)) {
            destination = this._mapManipulator.FindDestinationByExternalId(pSelectedDestination.id);
        } else if (pSelectedDestination && !isNaN(pSelectedDestination)) {
            destination = this._mapManipulator.getDestination(pSelectedDestination);
        }
        if (destination) {
            this._mapManipulator.setCurrentDestinationId(destination.Id);
            return;
        }

        this._mapManipulator.setCurrentDestinationId(null);
    };

    /**
     * sets the container context where the viewer gonna run
     *
     * @param {jQuery} pContainer
     */
    VDCanvasViewerBootstrap.prototype.setContainer = function(pContainer) {
        this._container = pContainer;

        this._eventManager.setPrefix(this._container.attr('id'));

        this._container.data(VDCanvasViewerBootstrap.DATA_KEY, this);

        // create html markup inside container element
        /*
         * <canvas id="vdMap" class="vdLayer" ></canvas> <div id="vdLoader"
         * class="vdLayer" ></div> <div id="vdUserInterface" > <div
         * id="buttonFloors" ></div> <div id="editMode" class="vdMapButton" ></div>
         * </div> <div id="destinationInfoArea" class="close" ></div> <div
         * id="editModePanel" class="close" ></div> <div id="youriPanel"
         * class="close" ></div>
         */

        var $canvasLayer = $('<canvas class="vdLayer vdMap"></canvas>').appendTo(this._container);

        this._canvas = new CanvasManager();

        this._canvas.MinimumScaleLimit = this.params.minZoomLevel;
        this._canvas.MaximumScaleLimit = this.params.maxZoomLevel;

        this._canvas.setCanvasElement($canvasLayer.get(0));
        this._canvas.setEventManager(this._eventManager);

    };

    /**
     * launch the process
     */
    VDCanvasViewerBootstrap.prototype.launch = function() {
        /** @var {MapLoader} mapLoader */
        var _mapLoader = new MapLoader(new ViadirectMap(), this._loadingQueue, this._eventManager);
        _mapLoader.modelReady = $.proxy(this.modelReady, this);

        // iterates all declared resources passed in params.resources object
        $.each(this.params.resources, $.proxy(_mapLoader.handleLoadResource, _mapLoader));

        var self = this;

        _mapLoader.setPathManager(this._pathManager);
        _mapLoader.start(self.params.model);

    };

    /**
     * when datas are fully loaded
     *
     * @param {ViadirectMap} pMap
     */
    VDCanvasViewerBootstrap.prototype.modelReady = function(pMap) {
        console.log("datasLoaded");

        this._canvas.Initialize("2d");

        this._canvas.setDrawLabels(this.params.showTitleBaselines);

        /**
         * @type StylesManager
         */
        var styleManager = new StylesManager();

        styleManager.selectorDestination = this.params.selectors.destinationStyle;
        styleManager.selectorShape = this.params.selectors.defaultShape;
        styleManager.selectorDefaultDestination = this.params.selectors.defaultDestination;
        styleManager.selectorTitleBaseline = this.params.selectors.defaultBaseline;
        styleManager.selectorDestinationTitleBaseline = this.params.selectors.destinationBaseline;

        // Initializing Map Model 
        new InitializeVDMap(pMap, styleManager, this.params.colorsMap, this.params.currentLanguageId).execute();
        /**
         * @type {MapManipulator}
         */
        this._mapManipulator = new MapManipulator(pMap, this._canvas, this._eventManager);
        // this._mapManipulator.Resources = pMap.Res;

        this._mapManipulator.setStyleManager(styleManager);
        this._mapManipulator.setEventManager(this._eventManager);

        this._mapManipulator.setDisplayItineraries(this.params.displayItineraries);



        this._canvas.setDestinationsInteractionsEnabled(this.params.destinationsInteractionsEnabled);
        this._canvas.setDestinationsPickInteractionsEnabled(this.params.destinationsPickInteractionsEnabled);
        this._canvas.setControlsInteractionsEnabled(this.params.controlsInteractionsEnabled);
        this._canvas.setInteractionsEnabled(this.params.interactionsEnabled);

        if (this.params.colorsMap.selected == null) {
            var selectedDestination = CSSHelper.FindCssRuleBySelector(styleManager.getShapeStyleName(StylesManager.STATE_ACTIVE));

            if (selectedDestination)
                if (selectedDestination.style)
                    if (selectedDestination.style.color)
                        this._mapManipulator.ColorsMap.selected = selectedDestination.style.color;
        } else {
            this._mapManipulator.ColorsMap.selected = this.params.colorsMap.selected;
        }

        if (this.params.colorsMap.over == null) {
            var overDestination = CSSHelper.FindCssRuleBySelector(styleManager.getShapeStyleName(StylesManager.STATE_HOVER));

            if (overDestination) {
                if (overDestination.style) {
                    if (overDestination.style.color) {
                        this._mapManipulator.ColorsMap.over = overDestination.style.color;
                    }
                }
            }
        } else {
            this._mapManipulator.ColorsMap.over = this.params.colorsMap.over;
        }

        if (this.params.selectedFloor !== null) {
            this._mapManipulator.setCurrentFloorName(this.params.selectedFloor);
        }

        if (this.params.externalIdKey !== null) {
            this._mapManipulator.setExternalIdKey(this.params.externalIdKey);
        }

        if (this.params.startingPathPoint !== null) {
            this.setStartingPathPoint(this.params.startingPathPoint);
        }

        if (this.params.currentLanguageId !== null)
            this._mapManipulator.CurrentLanguageId = this.params.currentLanguageId;

        if (this.params.currentLanguageId !== null) {
            this._mapManipulator.CurrentLanguageId = this.params.currentLanguageId;
        }

        if (this.params.scaleMode != null) {
            this._mapManipulator.ScaleMode = this.params.scaleMode;
        }

        if (this.params.zoomLevel !== null) {
            //Pruebas jose
            console.log("Se establece zoomLevel a "+this.params.zoomLevel);

            this._canvas.Scale = this.params.zoomLevel;
            this._canvas.DefaultScaleValue = this.params.zoomLevel;
            
        }

        if (this.params.destination && this.params.destination.hiddenPrefix !== null) {
            // injection in prototype
            Destination.prototype.hiddenPrefix = this.params.destination.hiddenPrefix
        }

        this._mapManipulator.Initialize();

        if (this.params.selectedDestination !== null) {
            this.setSelectedDestination(this.params.selectedDestination);
        }

        if (this.params.selectedDestinations !== null) {
            this._mapManipulator.setSelectedDestinations(this.params.selectedDestinations);
        }


        this._eventManager.bind("vdUIFloorChange", $.proxy(function(event, floorName) {
            this._mapManipulator.floorChanged(floorName);
        }, this));

        this._eventManager.bind("vdUnselectDestination", $.proxy(function(event) {
            this._mapManipulator.setCurrentDestinationId(-1);
        }, this));

        this._eventManager.trigger("vdActivateFloor", [this._mapManipulator.FloorName]);

        this._mapManipulator.invalidateDraw();

        this._eventManager.trigger('vdModelReady', [this._mapManipulator]);
    };

    /**
     *
     * @returns {Object}
     */
    VDCanvasViewerBootstrap.prototype.getParameters = function() {
        return this.params;
    };

    /**
     *
     * @returns {Boolean}
     */
    VDCanvasViewerBootstrap.prototype.getInteractionsEnabled = function() {
        return this._canvas.getInteractionsEnabled();
    };

    /**
     *
     * @param {Event} ev
     * @param {Number[]} destinations an array of destination ids
     */
    VDCanvasViewerBootstrap.prototype.selectDestinations = function(ev, destinations) {
        if (ev.type == 'vdSelectDestination') {
            this.setSelectedDestination(destinations[0]);
        } else {
            this._mapManipulator.setSelectedDestinations(destinations);
        }
    };

    /**
     *
     * @param {Event} ev
     * @param {Boolean} PMRMode
     */
    VDCanvasViewerBootstrap.prototype.setPMRMode = function(ev, PMRMode) {
        this._mapManipulator.setPMRMode(PMRMode);
    };

    /**
     *
     * @param {Event} ev
     * @param {Number|Object} leftId
     * @param {Number|Object} rightId
     */
    VDCanvasViewerBootstrap.prototype.requestItinerary = function(ev, leftId, rightId) {
        var dest;
        if (isNaN(leftId) && 'id' in leftId && 'fieldName' in leftId) {
            leftId.externalDestinationId = leftId.id;
            this.setStartingPathPoint(leftId);
        } else if (isNaN(leftId) && 'id' in leftId && !('fieldName' in leftId)) {
            this.setStartingPathPoint({
                destinationId: leftId.id
            });
        } else if (!isNaN(leftId)) {
            this.setStartingPathPoint({
                destinationId: leftId
            });
        }
        if (this._mapManipulator._startingPoint) {
            if (rightId !== null && isNaN(rightId)) {
                if ((rightId.constructor !== Number && rightId.constructor !== String && rightId.constructor !== Boolean)) { // ensure it is not a primitive ?
                    if ('id' in rightId && 'fieldName' in rightId) {
                        dest = this._mapManipulator.FindDestinationByExternalId(rightId.id, rightId.fieldName);
                        if (dest) rightId = dest.Id;
                        else rightId = null;
                    } else if ('externalDestinationId' in rightId && 'fieldName' in rightId) {
                        dest = this._mapManipulator.FindDestinationByExternalId(rightId.externalDestinationId, rightId.fieldName);
                        if (dest) rightId = dest.Id;
                        else rightId = null;
                    }
                }
            }
        } else {
            rightId = null;
        }

        this._mapManipulator.setCurrentPath(rightId);
        this.setSelectedDestination(rightId);
        this._mapManipulator.invalidateDraw();
    };

    /**
     * returns an object which gives a state of the visual object about the map
     */
    VDCanvasViewerBootstrap.prototype.getVisualState = function() {
        return this._canvas.getVisualState();
    };

    // All urls and paths to navigate in the map

    /*
     * Text Ressources related constants
     */

    var TEXT_RESSOURCES_PATH_TO_NAME_TRANSLATION_TEXT = "Text";

    var TEXT_RESSOURCES_PATH_TO_NAME_TRANSLATION_ABBREVIATION = "Abbreviation";

    /*
     * Destination related constants
     */

    var DESTINATION_FROM_ROOT_PATH_TO_ID = "Id";
    var DESTINATION_FROM_ROOT_PATH_TO_TRANSLATIONS = "TextResource/Translations";
    var DESTINATION_FROM_ROOT_PATH_TO_NAME = "TextResource/Name";
    var DESTINATION_FROM_ROOT_PATH_TO_DESCRIPTION = "Description/Translations";
    var DESTINATION_FROM_ROOT_PATH_TO_HIDDEN_TOPICS = "HiddenTopics";
    var DESTINATION_FROM_ROOT_PATH_TO_TOPICS = "Topics";
    var DESTINATION_FROM_ROOT_PATH_TO_CATEGORIES = "Categories";
    var DESTINATION_FROM_ROOT_PATH_TO_ACTUAL_SHAPE_LIST = "ShapeIndexes/ActualShapeList";


    var DESTINATION_PATH_URL = "Destinations/";

    var DESTINATION_LOGO_URL_PREFIX = "MapLogo_";

    var DESTINATION_PATH_TO_ROOT = "Value";

    var DESTINATION_PATH_TO_NAME = "Value/TextResource/Name";

    var DESTINATION_PATH_TO_TRANSLATIONS = "Value/TextResource/Translations";

    var DESTINATION_PATH_TO_DESCRIPTION = "Value/Description/Translations";

    var DESTINATION_PATH_TO_ID = "Value/Id";

    var DESTINATION_PATH_TO_HIDDEN_TOPICS = "Value/HiddenTopics";

    var DESTINATION_PATH_TO_TOPICS = "Value/Topics";

    var DESTINATION_PATH_TO_CATEGORIES = "Value/Categories";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS = "ExternalDatas";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA = "Data";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_KEY = "key";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_VALUE_UND = "und";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_VALUE_EN = "en";

    var DESTINATION_PATH_TO_EXTERNAL_DATAS_DATA_VALUE_RU = "ru";

    var DESTINATION_PATH_TO_ACTUAL_SHAPE_LIST = "Value/ShapeIndexes/ActualShapeList";

    var DESTINATION_PATH_TO_ACTUAL_SHAPE_LIST_FLOOR_ID = "FloorId";

    var DESTINATION_PATH_TO_ACTUAL_SHAPE_LIST_ID = "Id";

    /*
     * Category related constants
     */

    var CATEGORY_PATH_URL = "Categories/";

    var CATEGORY_PATH_TO_NAME = "TextResource/Name";

    var CATEGORY_PATH_TO_ID = "Id";

    var CATEGORY_PATH_TO_TEXT_RESSOURCES = "TextResource/Translations";

    /*
     * Path points related constants
     */

    var PATHPOINTS_PATH_POSITION = "Position";

    var PATHPOINTS_PATH_FLOOR_REFERENCE = "Floor";

    var PATHPOINTS_PATH_DESTINATION_REFERENCE = "DestinationReference";

    var PATHPOINTS_PATH_ID = "Id";

    var PATHPOINTS_PATH_FLOOR_POSITION = "Floor";

    var PATHPOINTS_PATH_NAME = "Name";

    /*
     * Path related constants
     */

    var PATH_PATH_STARTING_POINT = "StartPointId";

    var PATH_PATH_ENDING_POINT = "FinishPointId";

    var PATH_PATH_ACESSIBLE_DISABLE = "AccesibleToDisabledPeople";

    var PATH_PATH_ID = "Id";

    var PATH_PATH_DIRECTION = "PathDirection";

    var PATH_PATH_FROM_DAY = "UsableFromDay";

    var PATH_PATH_FROM_HOUR = "UsableFromHour";

    var PATH_PATH_TILL_DAY = "UsableTillDay";

    var PATH_PATH_TILL_HOUR = "UsableTillHour";

    /*
     * Topic related constants
     */

    var TOPIC_PATH_URL = "Topics/";

    var TOPIC_PATH_TO_NAME = "TextResource/Name";

    var TOPIC_PATH_TO_ID = "Id";

    var TOPIC_PATH_TO_TEXT_RESSOURCES = "TextResource/Translations";

    var TOPIC_PATH_TO_COLOR = "Color";

    var TOPIC_PATH_TO_PACKED_COLOR = "Color/packedValue";

    /*
     * Hidden Topic related constants
     */

    var HIDDEN_TOPIC_PATH_URL = "HiddenTopics/";

    /*
     * Floors related constants
     */

    // Floors folder
    var FLOORS_PATH_URL = "Floors/";

    // Texture Path Url
    var FLOOR_TEXTURE_PATH_URL = "Texture.png";

    // Lower point label in Json
    var FLOOR_LOWER_POINT_LABEL = "LowerPoint";

    // Lower point path from floor
    var FLOOR_LOWER_POINT_PATH = "LowerPoint";

    // Position path from floor
    var FLOOR_POSITION_PATH = "Position";

    var FLOOR_TEXTURE_HEIGHT = "Height";

    var FLOOR_TEXTURE_WIDTH = "Width";

    // Upper point label in Json
    var FLOOR_UPPER_POINT_LABEL = "UpperPoint";

    // Upper point path from floor
    var FLOOR_UPPER_POINT_PATH = "UpperPoint";

    // Path to Floor Name From Floor
    var FLOOR_PATH_TO_NAME = "TextResource/Name";

    var FLOOR_PATH_TO_TEXTURE_NAME = "Texture";

    var FLOOR_PATH_TO_PACKED_COLOR = "Color/packedValue";

    var FLOOR_PATH_TO_NAME_TRANSLATIONS = "TextResource/Translations";

    // Path to Shapes Array from Floor
    var FLOOR_PATH_TO_SHAPES = "Shapes";

    // Path to Shapes points from Shape
    var FLOOR_PATH_TO_SHAPE_POINTS = "Value/ShapePoints";
    var FLOOR_PATH_TO_SHAPE_ID = "Value/Id";
    var FLOOR_PATH_TO_SHAPE_COLOR_PACKED_VALUE = "Value/Color/packedValue";
    var FLOOR_PATH_TO_SHAPE_SELECTABLE = "Value/Selectable";
    var FLOOR_PATH_TO_SHAPE_HEIGHT = "Value/Height";
    var FLOOR_PATH_TITLE_BASELINE = "Value/TitleBaseline/PointList";
    var FLOOR_PATH_CENTER_POINT = "Value/Center";
    var FLOOR_PATH_TITLE_BASELINE_MAXHEIGHT = "Value/TitleBaseline/MaxTextHeight";

    /*
     * Building related constants
     */

    // Building.json Name
    var BUILDING_JSON = "building.json";

    // Floors label in Json
    var BUILDING_FLOORS_LABEL = "Floors";

    // Path to floors in building.json
    var BUILDING_FLOORS_PATH = "Floors";

    var BUILDING_PATH_TO_DESTINATIONS = "Destinations";

    var BUILDING_PATH_TO_CATEGORIES = "CategoriesId";

    var BUILDING_PATH_TO_TOPICS = "TopicsId";

    var BUILDING_PATH_TO_HIDDEN_TOPICS = "HiddenTopicsId";

    /*
     * Canvas related Constants
     */

    var CANVAS_DRAWABLE_BACKGROUND_TEXTURE = "BackgroundTexture";

    var CANVAS_DRAWABLE_VIADIRECT_SHAPE = "ViaDirectShape";

    var CANVAS_DRAWABLE_VIADIRECT_PATHPOINT = "ViaDirectPathPoint";

    var CANVAS_DRAWABLE_VIADIRECT_PATH = "ViaDirectPath";

    var CANVAS_DRAWABLE_VIADIRECT_CIRCLE = "ViaDirectCircle";

    var CANVAS_DRAWABLE_PICTO = "ViaDirectPicto";

    var CANVAS_DRAWABLE_BILBOARD = "ViaDirectBillboard";

    var CANVAS_DRAWABLE_LINE = "Line";

    var CANVAS_LABEL_BLOC = "BlockLabel";

    /*
     * Url Parameters Constants
     */

    var URL_SELECT_DESTINATION = "destination";

    var URL_SELECT_FLOOR = "floor";

    var URL_UI_STATUS = "ui";

    var CONSTANT_DOORS_CATEGORY = "doors";
    /**
     * vdCanvasViewer Class
     *
     * The vdCanvasViewer is a JQuery module. So deploy it from a Jquery Element like this $(element).vdCanvasViewer( params ).
     * The module will create in this elements all the html he needs to be fully fonctionnal and try to load
     * and draw the map. If no floor is specified in the property of the param object selectedFloor, if will
     * draw the first one he founds in the building.json.
     *
     * During the loading of the map, a loader canvas will be displayed above the future vdMapCanvas.
     *
     * Events system in the vdCanvasViewer :
     * We use JQuery events to communicate between our differents data structures. In order to avoid conflicts
     * if there is more than only one vdCanvasViewer on the webpage, we use an EventManagerDecorator class which
     * will prefix all the events associated with an instance of the vdCanvasViewer.
     * So, the structure of a complete event name is prefix + eventStandardName. To be more easily readable, will
     * will only list here all the events' standard Names. If you want to call theses event yourself, you will
     * have to add the prefix yourself or get the instance of the EventManagerDecorator.
     *
     * HTML Deployed structure :
     *
     * <code>
     * <div class="vd-viewer"> // placehoder
     *     <canvas class="vdLayer vdMap" height="396" width="596" style="display: block;"></canvas> // the canvas used to draw the Viadirect Map. The vdLayerClass is used to stack if with other vdLayer
     *     <div class="vdLoader vdLayer" style="display: none;">  // Another vdLayerClass, with the loader displayed during the map loading
     *         <canvas class="sonic" height="360" width="360"></canvas> // loader Canvas Draw with the Sonic JS lib
     *     </div>
     *     <div class="vdUserInterface"> // user interface elements
     *         <div class="buttonFloors"> // buttons used to change the floor. data-vdmapfloor-id contains the floor name
     *             <div class="vdMapButton vdMapFloor" data-vdmapbutton="changeFloor" data-vdmapfloor-id="0">
     *                 <span>0</span>
     *             </div>
     *             <div class="vdMapButton vdMapZoom" data-vdmapbutton="zoomIn"> // Zoom In button
     *                 <span>+</span>
     *             </div>
     *             <div class="vdMapButton vdMapZoom" data-vdmapbutton="zoomOut"> // Zoom Out button
     *                 <span>-</span>
     *             </div>
     *         </div>
     *     </div>
     *     <div class="destinationInfoArea close"> // Destination area, where the destinations infos will be displayed
     *     </div>
     * </div>
     * </code>
     *
     * VD mouse picking events
     *     @event mousePicking : warn that the click was on the Canvas, so a destination can be pick
     *     @param {Point} : the canvas point picked by the click ( Zoom / resize / drag Matrix are applied )
     *         @property {float} X
     *         @property {float} Y
     *     @event vdDestinationPicked : A destination has been picked !
     *     @param {object}
     *         @property {int} destinationId : viadirect id of the destination picked
     *         @property {int] shapeId : id of the shape picked
     *
     * VD User interface events
     *     @event LayerChange : event trigger at the end of a layer transition
     *     @param {string} layerName : the name of the activated layer
     *     @event vdUIFloorChanged : signal sent to the canvas to indicate a changing floor
     *     @param {string} floorId : id of the new floor
     *
     * VD custom actions events on Canvas
     *     @event vdClick : handle the click action on the canvas. Trigger the mousepicking event
     *                      and logic if the click is on the canvas.
     *     @param originalEvent : the original click event
     *     @event vdMouseup : handle the mouseup action on the canvas. Control the map drag.
     *     @param originalEvent : the original click event
     *     @event vdMousemove : handle the mouseup action on the canvas. Control the map drag.
     *     @param originalEvent : the original click event
     *     @event vdMouseout : handle the mouseup action on the canvas. Control the map drag.
     *     @param originalEvent : the original click event
     *     @event vdMousedown : handle the mouseup action on the canvas. Control the map drag.
     *     @param originalEvent : the original click event
     *     @event vdMousewheel : handle the mouseup action on the canvas. Control the map Zoom.
     *     @param originalEvent : the original click event
     *     @event vdResize : handle the mouseup action on the canvas. Resize and redraw the canvas.
     *     @param vdUIZoomIn : Zoom In
     *     @event vdUIZoomOut : Zoom Out
     *
     * Loading Map events:
     *     @param AllMapIsLoaded : signals that all the map has been successfully loaded
     *     @param ErrorLoadingMap : signals that the loading of the map has been aborted due to an error
     *
     * @file jquery.vd-canvas.viewer.js
     * @require viadirect/io/PathManager.js
     * @require viadirect/VDCanvasViewerBootstrap.js
     */

    /**
     * @var {Object} unserialized querystring params
     */

    var defaultParams = {
        /**
         * @param {object} params : Use this object to set the parameters of the map
         *                canvas.
         * @property {object} loader : object allowing the module to retreive the
         *           map data from differents streams. If you are using the standard
         *           zip map structure, you only need to specify the hostname, path
         *           and protocol. Otherwise, you can overload any specified url in
         *           the parameter object
         * @property {string} hostName : the host name of the flux's urls
         * @default ""
         *
         * @property {string} protocol : the protocol of the flux's urls
         * @default "http"
         *
         * @property {string} path : the url path of the root of the map zip
         * @default "/"
         *
         * @property {string} buildingFilename
         * @default "building.json"
         *
         * @property {string} floorPath
         * @default "Floors"
         *
         * @property {string} floorTextureName
         * @default "Texture.png"
         *
         * @property {string} destinationPath
         * @default "Destinations"
         *
         * @property {string} categoryPath
         * @default "Categories"
         *
         * @property {string} topicsPath
         * @default "Topics"
         *
         * @property {string} hiddenTopicsPath
         * @default "HiddenTopics"
         *
         * @property {string} selectedFloor - the floor's name to set the focus on
         *           when the map is loaded. This floor name may need to be
         *           specified when you are using the SelectedDestination property
         *           on a multiple floors destination.
         * @default null
         *
         * @property {object} startingPoint - the starting point id or the
         *           destination's id as a starting point.
         * @default { pointId: null, destinationId: null, externalDestinationId:
         *          null }
         *
         * @property {string} externalIdKey - The field name for destinations external identifier
         *           External identifier are used when the map model is builtand populated with data from streams comin
         *           from the client's information system and corresponds to the id in the client information system.
         * @default  null
         *
         * @property {number} selectedDestination - viadirect ID of the destination.
         *           This destination will be selected at the opening of the map,
         *           and the camera (field of view) will show this destination if
         *           and only if it is on the currently displayed floor. Use the
         *           property "selectedFloor" to set it.
         * @default null
         *
         * @property {boolean} interactionsEnabled - allow or not the user interactions
         *           with the canvas map
         * @default true
         *
         * @property {number} zoomLevel - sets the first zoom level when the map is
         *           opened for the first time
         * @default 1
         *
         * @property {number} currentLanguageId - sets the id of the language of the
         *           canvas
         * @default 0
         */
        loader: {
            hostName: "",
            protocol: "http",
            path: "/",
            buildingFilename: "building.json",
            floorPath: "Floors",
            floorTextureName: "Texture.png",
            destinationPath: "Destinations",
            categoryPath: "Categories",
            topicsPath: "Topics",
            hiddenTopicsPath: "HiddenTopics"
        },
        resources: {
            ArrowIcon: ".vd-viewer > .icon-arrow",
            EscalatorUp: ".vd-viewer > .icon-escalator-up",
            EscalatorDown: ".vd-viewer > .icon-escalator-down",
            StairsUp: ".vd-viewer > .icon-stairs-up",
            StairsDown: ".vd-viewer > .icon-stairs-down",
            ElevatorUp: ".vd-viewer > .icon-elevator-up",
            ElevatorDown: ".vd-viewer > .icon-elevator-down",
            YouAreHere: ".vd-viewer > .icon-you-are-here"
        },
        pathManager: null,
        model: null,
        userInterface: null,
        userInterfaceParams: {},
        destination: {
            hiddenPrefix: null
        },
        startingPathPoint: {
            pointId: null,
            destinationId: null,
            externalDestinationId: null
        },
        externalIdKey: null,
        showTitleBaselines: true,
        selectedFloor: null,
        selectedDestinations: null,
        selectedDestination: null,
        colorsMap: {
            selected: null,
            over: null,
            titleSelected: null,
            titleOver: null
        },
        selectors: {
            defaultBaseline: "canvas > .destination > label",
            destinationBaseline: "canvas > .destination-{id} > label",
            selectedColor: "canvas > .selectedColor",
            defaultShape: "canvas > .shape",
            defaultDestination: "canvas > .destination",
            destinationStyle: "canvas > .destination-{id}",
        },
        displayItineraries: true,
        openUiOnSelectedDestination: true,
        interactionsEnabled: true,
        controlsInteractionsEnabled: true,
        destinationsInteractionsEnabled: true,
        destinationsPickInteractionsEnabled: true,
        scaleMode: "fixedZoom",
        zoomLevel: 1,
        maxZoomLevel: 2,
        minZoomLevel: 0.25,
        currentLanguageId: 0
    };

    $.fn.vdCanvasViewer = function(params) {
        if (typeof params == "object") {
            if (defaultParams.pathManager == null)
                defaultParams.pathManager = new PathManager();
            params = $.extend(true, {}, defaultParams, params);
            params.pathManager.setParams(params.loader);

            this.each(generateViewer, [params, params.pathManager]);
        } else {
            // use case $('.vd-viewer').vdCavnasViewer('zoomIn');
            this.each(executeMethod, arguments);
        }
        return this;
    };

    /**
     *
     * @param {Object}
     *                pParams parameters object
     * @param {PathManager}
     *                pPathManager
     */
    function generateViewer(pParams, pPathManager) {
        if (!$(this).data(VDCanvasViewerBootstrap.DATA_KEY)) {
            var bootstrapper = new VDCanvasViewerBootstrap(pParams);
            bootstrapper.setPathManager(pPathManager);
            bootstrapper.setEventManager($(document), $(this));
            bootstrapper.setContainer($(this));
            if (pParams.hasOwnProperty("userInterface") && pParams.userInterface !== null) {
                bootstrapper.setUserInterface(pParams.userInterface);
            }
            $(global).on('orientationchange', function() {
                bootstrapper.getEventManager().trigger("vdOrientationChange");
                console.log("orientation.change now ", window.screen.orientation.angle);
            });

            $(global).on('resize', function(ev) {
                bootstrapper.getEventManager().trigger("vdResize");
            });
            bootstrapper.launch();
        }
        console.log("generateViewer::" + this);
    }

    function executeMethod(pCommandName) {
        var impl = $(this).data(VDCanvasViewerBootstrap.DATA_KEY);

        if (impl && pCommandName in impl) {
            var args = Array.prototype.slice.call(arguments, 1);
            return impl[pCommandName].apply(impl, args);
        }
    }

    /**
     * crossbrowser requestAnimationFrame
     *
     */
    window.requestAnimFrame = (function() {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function( /* function */ callback, /* DOMElement */ element) {
            return window.setTimeout(callback, 1000 / 60);
        };
    })();

})(jQuery, window);
