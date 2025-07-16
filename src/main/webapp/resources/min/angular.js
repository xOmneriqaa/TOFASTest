/*! angular-breadcrumb - v0.4.0-dev-2015-08-07
* http://ncuillery.github.io/angular-breadcrumb
* Copyright (c) 2015 Nicolas Cuillery; Licensed MIT */

(function (window, angular, undefined) {
'use strict';

function isAOlderThanB(scopeA, scopeB) {
    if(angular.equals(scopeA.length, scopeB.length)) {
        return scopeA > scopeB;
    } else {
        return scopeA.length > scopeB.length;
    }
}

function parseStateRef(ref) {
    var parsed = ref.replace(/\n/g, " ").match(/^([^(]+?)\s*(\((.*)\))?$/);
    if (!parsed || parsed.length !== 4) { throw new Error("Invalid state ref '" + ref + "'"); }
    return { state: parsed[1], paramExpr: parsed[3] || null };
}

function $Breadcrumb() {

    var $$options = {
        prefixStateName: null,
        template: 'bootstrap3',
        templateUrl: null,
        includeAbstract : false
    };

    this.setOptions = function(options) {
        angular.extend($$options, options);
    };

    this.$get = ['$state', '$stateParams', '$rootScope', function($state, $stateParams, $rootScope) {

        var $lastViewScope = $rootScope;

        // Early catch of $viewContentLoaded event
        $rootScope.$on('$viewContentLoaded', function (event) {
            // With nested views, the event occur several times, in "wrong" order
            if(!event.targetScope.ncyBreadcrumbIgnore &&
                isAOlderThanB(event.targetScope.$id, $lastViewScope.$id)) {
                $lastViewScope = event.targetScope;
            }
        });

        // Get the parent state
        var $$parentState = function(state) {
            // Check if state has explicit parent OR we try guess parent from its name
            var parent = state.parent || (/^(.+)\.[^.]+$/.exec(state.name) || [])[1];
            var isObjectParent = typeof parent === "object";
            // if parent is a object reference, then extract the name
            return isObjectParent ? parent.name : parent;
        };

        // Add the state in the chain if not already in and if not abstract
        var $$addStateInChain = function(chain, stateRef) {
            var conf,
                parentParams,
                ref = parseStateRef(stateRef),
                force = false,
                skip = false;

            for(var i=0, l=chain.length; i<l; i+=1) {
                if (chain[i].name === ref.state) {
                    return;
                }
            }

            conf = $state.get(ref.state);
            // Get breadcrumb options
            if(conf.ncyBreadcrumb) {
                if(conf.ncyBreadcrumb.force){ force = true; }
                if(conf.ncyBreadcrumb.skip){ skip = true; }
            }
            if((!conf.abstract || $$options.includeAbstract || force) && !skip) {
                if(ref.paramExpr) {
                    parentParams = $lastViewScope.$eval(ref.paramExpr);
                }

                conf.ncyBreadcrumbLink = $state.href(ref.state, parentParams || $stateParams || {});
                chain.unshift(conf);
            }
        };

        // Get the state for the parent step in the breadcrumb
        var $$breadcrumbParentState = function(stateRef) {
            var ref = parseStateRef(stateRef),
                conf = $state.get(ref.state);

            if(conf.ncyBreadcrumb && conf.ncyBreadcrumb.parent) {
                // Handle the "parent" property of the breadcrumb, override the parent/child relation of the state
                var isFunction = typeof conf.ncyBreadcrumb.parent === 'function';
                var parentStateRef = isFunction ? conf.ncyBreadcrumb.parent($lastViewScope) : conf.ncyBreadcrumb.parent;
                if(parentStateRef) {
                    return parentStateRef;
                }
            }

            return $$parentState(conf);
        };

        return {

            getTemplate: function(templates) {
                if($$options.templateUrl) {
                    // templateUrl takes precedence over template
                    return null;
                } else if(templates[$$options.template]) {
                    // Predefined templates (bootstrap, ...)
                    return templates[$$options.template];
                } else {
                    return $$options.template;
                }
            },

            getTemplateUrl: function() {
                return $$options.templateUrl;
            },

            getStatesChain: function(exitOnFirst) { // Deliberately undocumented param, see getLastStep
                var chain = [];

                // From current state to the root
                for(var stateRef = $state.$current.self.name; stateRef; stateRef=$$breadcrumbParentState(stateRef)) {
                    $$addStateInChain(chain, stateRef);
                    if(exitOnFirst && chain.length) {
                        return chain;
                    }
                }

                // Prefix state treatment
                if($$options.prefixStateName) {
                    $$addStateInChain(chain, $$options.prefixStateName);
                }

                return chain;
            },

            getLastStep: function() {
                var chain = this.getStatesChain(true);
                return chain.length ? chain[0] : undefined;
            },

            $getLastViewScope: function() {
                return $lastViewScope;
            }
        };
    }];
}

var getExpression = function(interpolationFunction) {
    if(interpolationFunction.expressions) {
        return interpolationFunction.expressions;
    } else {
        var expressions = [];
        angular.forEach(interpolationFunction.parts, function(part) {
            if(angular.isFunction(part)) {
                expressions.push(part.exp);
            }
        });
        return expressions;
    }
};

var registerWatchers = function(labelWatcherArray, interpolationFunction, viewScope, step) {
    angular.forEach(getExpression(interpolationFunction), function(expression) {
        var watcher = viewScope.$watch(expression, function() {
            step.ncyBreadcrumbLabel = interpolationFunction(viewScope);
        });
        labelWatcherArray.push(watcher);
    });

};

var deregisterWatchers = function(labelWatcherArray) {
    angular.forEach(labelWatcherArray, function(deregisterWatch) {
        deregisterWatch();
    });
};

function BreadcrumbDirective($interpolate, $breadcrumb, $rootScope) {
    var $$templates = {
        bootstrap2: '<ul class="breadcrumb">' +
            '<li ng-repeat="step in steps" ng-switch="$last || !!step.abstract" ng-class="{active: $last}">' +
            '<a ng-switch-when="false" href="{{step.ncyBreadcrumbLink}}">{{step.ncyBreadcrumbLabel}}</a>' +
            '<span ng-switch-when="true">{{step.ncyBreadcrumbLabel}}</span>' +
            '<span class="divider" ng-hide="$last">/</span>' +
            '</li>' +
            '</ul>',
        bootstrap3: '<ol class="breadcrumb">' +
            '<li ng-repeat="step in steps" ng-class="{active: $last}" ng-switch="$last || !!step.abstract">' +
            '<a ng-switch-when="false" href="{{step.ncyBreadcrumbLink}}">{{step.ncyBreadcrumbLabel}}</a>' +
            '<span ng-switch-when="true">{{step.ncyBreadcrumbLabel}}</span>' +
            '</li>' +
            '</ol>'
    };

    return {
        restrict: 'AE',
        replace: true,
        scope: {},
        template: $breadcrumb.getTemplate($$templates),
        templateUrl: $breadcrumb.getTemplateUrl(),
        link: {
            post: function postLink(scope) {
                var labelWatchers = [];

                var renderBreadcrumb = function() {
                    deregisterWatchers(labelWatchers);
                    labelWatchers = [];
                    
                    var viewScope = $breadcrumb.$getLastViewScope();
                    scope.steps = $breadcrumb.getStatesChain();
                    angular.forEach(scope.steps, function (step) {
                        if (step.ncyBreadcrumb && step.ncyBreadcrumb.label) {
                            var parseLabel = $interpolate(step.ncyBreadcrumb.label);
                            step.ncyBreadcrumbLabel = parseLabel(viewScope);
                            // Watcher for further viewScope updates
                            registerWatchers(labelWatchers, parseLabel, viewScope, step);
                        } else {
                            step.ncyBreadcrumbLabel = step.name;
                        }
                    });
                };

                $rootScope.$on('$viewContentLoaded', function (event) {
                    if(!event.targetScope.ncyBreadcrumbIgnore) {
                        renderBreadcrumb();
                    }
                });

                // View(s) may be already loaded while the directive's linking
                renderBreadcrumb();
            }
        }
    };
}
BreadcrumbDirective.$inject = ['$interpolate', '$breadcrumb', '$rootScope'];

function BreadcrumbLastDirective($interpolate, $breadcrumb, $rootScope) {

    return {
        restrict: 'A',
        scope: {},
        template: '{{ncyBreadcrumbLabel}}',
        compile: function(cElement, cAttrs) {

            // Override the default template if ncyBreadcrumbLast has a value
            var template = cElement.attr(cAttrs.$attr.ncyBreadcrumbLast);
            if(template) {
                cElement.html(template);
            }

            return {
                post: function postLink(scope) {
                    var labelWatchers = [];

                    var renderLabel = function() {
                        deregisterWatchers(labelWatchers);
                        labelWatchers = [];
                        
                        var viewScope = $breadcrumb.$getLastViewScope();
                        var lastStep = $breadcrumb.getLastStep();
                        if(lastStep) {
                            scope.ncyBreadcrumbLink = lastStep.ncyBreadcrumbLink;
                            if (lastStep.ncyBreadcrumb && lastStep.ncyBreadcrumb.label) {
                                var parseLabel = $interpolate(lastStep.ncyBreadcrumb.label);
                                scope.ncyBreadcrumbLabel = parseLabel(viewScope);
                                // Watcher for further viewScope updates
                                // Tricky last arg: the last step is the entire scope of the directive !
                                registerWatchers(labelWatchers, parseLabel, viewScope, scope);
                            } else {
                                scope.ncyBreadcrumbLabel = lastStep.name;
                            }
                        }
                    };

                    $rootScope.$on('$viewContentLoaded', function (event) {
                        if(!event.targetScope.ncyBreadcrumbIgnore) {
                            renderLabel();
                        }
                    });

                    // View(s) may be already loaded while the directive's linking
                    renderLabel();
                }
            };

        }
    };
}
BreadcrumbLastDirective.$inject = ['$interpolate', '$breadcrumb', '$rootScope'];

function BreadcrumbTextDirective($interpolate, $breadcrumb, $rootScope) {

    return {
        restrict: 'A',
        scope: {},
        template: '{{ncyBreadcrumbChain}}',

        compile: function(cElement, cAttrs) {
            // Override the default template if ncyBreadcrumbText has a value
            var template = cElement.attr(cAttrs.$attr.ncyBreadcrumbText);
            if(template) {
                cElement.html(template);
            }
            
            var separator = cElement.attr(cAttrs.$attr.ncyBreadcrumbTextSeparator) || ' / ';

            return {
                post: function postLink(scope) {
                    var labelWatchers = [];
                    
                    var registerWatchersText = function(labelWatcherArray, interpolationFunction, viewScope) {
                        angular.forEach(getExpression(interpolationFunction), function(expression) {
                            var watcher = viewScope.$watch(expression, function(newValue, oldValue) {
                                if (newValue !== oldValue) {
                                    renderLabel();
                                }
                            });
                            labelWatcherArray.push(watcher);
                        });
                    };

                    var renderLabel = function() {
                        deregisterWatchers(labelWatchers);
                        labelWatchers = [];
                        
                        var viewScope = $breadcrumb.$getLastViewScope();
                        var steps = $breadcrumb.getStatesChain();
                        var combinedLabels = [];
                        angular.forEach(steps, function (step) {
                            if (step.ncyBreadcrumb && step.ncyBreadcrumb.label) {
                                var parseLabel = $interpolate(step.ncyBreadcrumb.label);
                                combinedLabels.push(parseLabel(viewScope));
                                // Watcher for further viewScope updates
                                registerWatchersText(labelWatchers, parseLabel, viewScope);
                            } else {
                                combinedLabels.push(step.name);
                            }
                        });
                        
                        scope.ncyBreadcrumbChain = combinedLabels.join(separator);
                    };

                    $rootScope.$on('$viewContentLoaded', function (event) {
                        if(!event.targetScope.ncyBreadcrumbIgnore) {
                            renderLabel();
                        }
                    });

                    // View(s) may be already loaded while the directive's linking
                    renderLabel();
                }
            };

        }
    };
}
BreadcrumbTextDirective.$inject = ['$interpolate', '$breadcrumb', '$rootScope'];

angular.module('ncy-angular-breadcrumb', ['ui.router.state'])
    .provider('$breadcrumb', $Breadcrumb)
    .directive('ncyBreadcrumb', BreadcrumbDirective)
    .directive('ncyBreadcrumbLast', BreadcrumbLastDirective)
    .directive('ncyBreadcrumbText', BreadcrumbTextDirective);
})(window, window.angular);

/**
 * @license AngularJS v1.3.15
 * (c) 2010-2014 Google, Inc. http://angularjs.org
 * License: MIT
 */
(function(window, angular, undefined) {'use strict';

    /**
     * @ngdoc module
     * @name ngMessages
     * @description
     *
     * The `ngMessages` module provides enhanced support for displaying messages within templates
     * (typically within forms or when rendering message objects that return key/value data).
     * Instead of relying on JavaScript code and/or complex ng-if statements within your form template to
     * show and hide error messages specific to the state of an input field, the `ngMessages` and
     * `ngMessage` directives are designed to handle the complexity, inheritance and priority
     * sequencing based on the order of how the messages are defined in the template.
     *
     * Currently, the ngMessages module only contains the code for the `ngMessages`
     * and `ngMessage` directives.
     *
     * # Usage
     * The `ngMessages` directive listens on a key/value collection which is set on the ngMessages attribute.
     * Since the {@link ngModel ngModel} directive exposes an `$error` object, this error object can be
     * used with `ngMessages` to display control error messages in an easier way than with just regular angular
     * template directives.
     *
     * ```html
     * <form name="myForm">
     *   <input type="text" ng-model="field" name="myField" required minlength="5" />
     *   <div ng-messages="myForm.myField.$error">
     *     <div ng-message="required">You did not enter a field</div>
     *     <div ng-message="minlength">The value entered is too short</div>
     *   </div>
     * </form>
     * ```
     *
     * Now whatever key/value entries are present within the provided object (in this case `$error`) then
     * the ngMessages directive will render the inner first ngMessage directive (depending if the key values
     * match the attribute value present on each ngMessage directive). In other words, if your errors
     * object contains the following data:
     *
     * ```javascript
     * <!-- keep in mind that ngModel automatically sets these error flags -->
     * myField.$error = { minlength : true, required : false };
     * ```
     *
     * Then the `required` message will be displayed first. When required is false then the `minlength` message
     * will be displayed right after (since these messages are ordered this way in the template HTML code).
     * The prioritization of each message is determined by what order they're present in the DOM.
     * Therefore, instead of having custom JavaScript code determine the priority of what errors are
     * present before others, the presentation of the errors are handled within the template.
     *
     * By default, ngMessages will only display one error at a time. However, if you wish to display all
     * messages then the `ng-messages-multiple` attribute flag can be used on the element containing the
     * ngMessages directive to make this happen.
     *
     * ```html
     * <div ng-messages="myForm.myField.$error" ng-messages-multiple>...</div>
     * ```
     *
     * ## Reusing and Overriding Messages
     * In addition to prioritization, ngMessages also allows for including messages from a remote or an inline
     * template. This allows for generic collection of messages to be reused across multiple parts of an
     * application.
     *
     * ```html
     * <script type="text/ng-template" id="error-messages">
     *   <div ng-message="required">This field is required</div>
     *   <div ng-message="minlength">This field is too short</div>
     * </script>
     * <div ng-messages="myForm.myField.$error" ng-messages-include="error-messages"></div>
     * ```
     *
     * However, including generic messages may not be useful enough to match all input fields, therefore,
     * `ngMessages` provides the ability to override messages defined in the remote template by redefining
     * then within the directive container.
     *
     * ```html
     * <!-- a generic template of error messages known as "my-custom-messages" -->
     * <script type="text/ng-template" id="my-custom-messages">
     *   <div ng-message="required">This field is required</div>
     *   <div ng-message="minlength">This field is too short</div>
     * </script>
     *
     * <form name="myForm">
     *   <input type="email"
     *          id="email"
     *          name="myEmail"
     *          ng-model="email"
     *          minlength="5"
     *          required />
     *
     *   <div ng-messages="myForm.myEmail.$error" ng-messages-include="my-custom-messages">
     *     <!-- this required message has overridden the template message -->
     *     <div ng-message="required">You did not enter your email address</div>
     *
     *     <!-- this is a brand new message and will appear last in the prioritization -->
     *     <div ng-message="email">Your email address is invalid</div>
     *   </div>
     * </form>
     * ```
     *
     * In the example HTML code above the message that is set on required will override the corresponding
     * required message defined within the remote template. Therefore, with particular input fields (such
     * email addresses, date fields, autocomplete inputs, etc...), specialized error messages can be applied
     * while more generic messages can be used to handle other, more general input errors.
     *
     * ## Animations
     * If the `ngAnimate` module is active within the application then both the `ngMessages` and
     * `ngMessage` directives will trigger animations whenever any messages are added and removed
     * from the DOM by the `ngMessages` directive.
     *
     * Whenever the `ngMessages` directive contains one or more visible messages then the `.ng-active` CSS
     * class will be added to the element. The `.ng-inactive` CSS class will be applied when there are no
     * animations present. Therefore, CSS transitions and keyframes as well as JavaScript animations can
     * hook into the animations whenever these classes are added/removed.
     *
     * Let's say that our HTML code for our messages container looks like so:
     *
     * ```html
     * <div ng-messages="myMessages" class="my-messages">
     *   <div ng-message="alert" class="some-message">...</div>
     *   <div ng-message="fail" class="some-message">...</div>
     * </div>
     * ```
     *
     * Then the CSS animation code for the message container looks like so:
     *
     * ```css
     * .my-messages {
 *   transition:1s linear all;
 * }
     * .my-messages.ng-active {
 *   // messages are visible
 * }
     * .my-messages.ng-inactive {
 *   // messages are hidden
 * }
     * ```
     *
     * Whenever an inner message is attached (becomes visible) or removed (becomes hidden) then the enter
     * and leave animation is triggered for each particular element bound to the `ngMessage` directive.
     *
     * Therefore, the CSS code for the inner messages looks like so:
     *
     * ```css
     * .some-message {
 *   transition:1s linear all;
 * }
     *
     * .some-message.ng-enter {}
     * .some-message.ng-enter.ng-enter-active {}
     *
     * .some-message.ng-leave {}
     * .some-message.ng-leave.ng-leave-active {}
     * ```
     *
     * {@link ngAnimate Click here} to learn how to use JavaScript animations or to learn more about ngAnimate.
     */
    angular.module('ngMessages', [])

    /**
     * @ngdoc directive
     * @module ngMessages
     * @name ngMessages
     * @restrict AE
     *
     * @description
     * `ngMessages` is a directive that is designed to show and hide messages based on the state
     * of a key/value object that it listens on. The directive itself compliments error message
     * reporting with the `ngModel` $error object (which stores a key/value state of validation errors).
     *
     * `ngMessages` manages the state of internal messages within its container element. The internal
     * messages use the `ngMessage` directive and will be inserted/removed from the page depending
     * on if they're present within the key/value object. By default, only one message will be displayed
     * at a time and this depends on the prioritization of the messages within the template. (This can
     * be changed by using the ng-messages-multiple on the directive container.)
     *
     * A remote template can also be used to promote message reusability and messages can also be
     * overridden.
     *
     * {@link module:ngMessages Click here} to learn more about `ngMessages` and `ngMessage`.
     *
     * @usage
     * ```html
     * <!-- using attribute directives -->
     * <ANY ng-messages="expression">
     *   <ANY ng-message="keyValue1">...</ANY>
     *   <ANY ng-message="keyValue2">...</ANY>
     *   <ANY ng-message="keyValue3">...</ANY>
     * </ANY>
     *
     * <!-- or by using element directives -->
     * <ng-messages for="expression">
     *   <ng-message when="keyValue1">...</ng-message>
     *   <ng-message when="keyValue2">...</ng-message>
     *   <ng-message when="keyValue3">...</ng-message>
     * </ng-messages>
     * ```
     *
     * @param {string} ngMessages an angular expression evaluating to a key/value object
     *                 (this is typically the $error object on an ngModel instance).
     * @param {string=} ngMessagesMultiple|multiple when set, all messages will be displayed with true
     * @param {string=} ngMessagesInclude|include when set, the specified template will be included into the ng-messages container
     *
     * @example
     * <example name="ngMessages-directive" module="ngMessagesExample"
     *          deps="angular-messages.js"
     *          animations="true" fixBase="true">
     *   <file name="index.html">
     *     <form name="myForm">
     *       <label>Enter your name:</label>
     *       <input type="text"
     *              name="myName"
     *              ng-model="name"
     *              ng-minlength="5"
     *              ng-maxlength="20"
     *              required />
     *
     *       <pre>myForm.myName.$error = {{ myForm.myName.$error | json }}</pre>
     *
     *       <div ng-messages="myForm.myName.$error" style="color:maroon">
     *         <div ng-message="required">You did not enter a field</div>
     *         <div ng-message="minlength">Your field is too short</div>
     *         <div ng-message="maxlength">Your field is too long</div>
     *       </div>
     *     </form>
     *   </file>
     *   <file name="script.js">
     *     angular.module('ngMessagesExample', ['ngMessages']);
     *   </file>
     * </example>
     */
        .directive('ngMessages', ['$compile', '$animate', '$templateRequest',
            function($compile,    $animate,   $templateRequest) {
                var ACTIVE_CLASS = 'ng-active';
                var INACTIVE_CLASS = 'ng-inactive';

                return {
                    restrict: 'AE',
                    controller: function() {
                        this.$renderNgMessageClasses = angular.noop;

                        var messages = [];
                        this.registerMessage = function(index, message) {
                            for (var i = 0; i < messages.length; i++) {
                                if (messages[i].type == message.type) {
                                    if (index != i) {
                                        var temp = messages[index];
                                        messages[index] = messages[i];
                                        if (index < messages.length) {
                                            messages[i] = temp;
                                        } else {
                                            messages.splice(0, i); //remove the old one (and shift left)
                                        }
                                    }
                                    return;
                                }
                            }
                            messages.splice(index, 0, message); //add the new one (and shift right)
                        };

                        this.renderMessages = function(values, multiple) {
                            values = values || {};

                            var found;
                            angular.forEach(messages, function(message) {
                                if ((!found || multiple) && truthyVal(values[message.type])) {
                                    message.attach();
                                    found = true;
                                } else {
                                    message.detach();
                                }
                            });

                            this.renderElementClasses(found);

                            function truthyVal(value) {
                                return value !== null && value !== false && value;
                            }
                        };
                    },
                    require: 'ngMessages',
                    link: function($scope, element, $attrs, ctrl) {
                        ctrl.renderElementClasses = function(bool) {
                            bool ? $animate.setClass(element, ACTIVE_CLASS, INACTIVE_CLASS)
                                : $animate.setClass(element, INACTIVE_CLASS, ACTIVE_CLASS);
                        };

                        //JavaScript treats empty strings as false, but ng-message-multiple by itself is an empty string
                        var multiple = angular.isString($attrs.ngMessagesMultiple) ||
                            angular.isString($attrs.multiple);

                        var cachedValues, watchAttr = $attrs.ngMessages || $attrs['for']; //for is a reserved keyword
                        $scope.$watchCollection(watchAttr, function(values) {
                            cachedValues = values;
                            ctrl.renderMessages(values, multiple);
                        });

                        var tpl = $attrs.ngMessagesInclude || $attrs.include;
                        if (tpl) {
                            $templateRequest(tpl)
                                .then(function processTemplate(html) {
                                    var after, container = angular.element('<div/>').html(html);
                                    angular.forEach(container.children(), function(elm) {
                                        elm = angular.element(elm);
                                        after ? after.after(elm)
                                            : element.prepend(elm); //start of the container
                                        after = elm;
                                        $compile(elm)($scope);
                                    });
                                    ctrl.renderMessages(cachedValues, multiple);
                                });
                        }
                    }
                };
            }])


    /**
     * @ngdoc directive
     * @name ngMessage
     * @restrict AE
     * @scope
     *
     * @description
     * `ngMessage` is a directive with the purpose to show and hide a particular message.
     * For `ngMessage` to operate, a parent `ngMessages` directive on a parent DOM element
     * must be situated since it determines which messages are visible based on the state
     * of the provided key/value map that `ngMessages` listens on.
     *
     * More information about using `ngMessage` can be found in the
     * {@link module:ngMessages `ngMessages` module documentation}.
     *
     * @usage
     * ```html
     * <!-- using attribute directives -->
     * <ANY ng-messages="expression">
     *   <ANY ng-message="keyValue1">...</ANY>
     *   <ANY ng-message="keyValue2">...</ANY>
     *   <ANY ng-message="keyValue3">...</ANY>
     * </ANY>
     *
     * <!-- or by using element directives -->
     * <ng-messages for="expression">
     *   <ng-message when="keyValue1">...</ng-message>
     *   <ng-message when="keyValue2">...</ng-message>
     *   <ng-message when="keyValue3">...</ng-message>
     * </ng-messages>
     * ```
     *
     * @param {string} ngMessage a string value corresponding to the message key.
     */
        .directive('ngMessage', ['$animate', function($animate) {
            var COMMENT_NODE = 8;
            return {
                require: '^ngMessages',
                transclude: 'element',
                terminal: true,
                restrict: 'AE',
                link: function($scope, $element, $attrs, ngMessages, $transclude) {
                    var index, element;

                    var commentNode = $element[0];
                    var parentNode = commentNode.parentNode;
                    for (var i = 0, j = 0; i < parentNode.childNodes.length; i++) {
                        var node = parentNode.childNodes[i];
                        if (node.nodeType == COMMENT_NODE && node.nodeValue.indexOf('ngMessage') >= 0) {
                            if (node === commentNode) {
                                index = j;
                                break;
                            }
                            j++;
                        }
                    }

                    ngMessages.registerMessage(index, {
                        type: $attrs.ngMessage || $attrs.when,
                        attach: function() {
                            if (!element) {
                                $transclude($scope, function(clone) {
                                    $animate.enter(clone, null, $element);
                                    element = clone;
                                });
                            }
                        },
                        detach: function(now) {
                            if (element) {
                                $animate.leave(element);
                                element = null;
                            }
                        }
                    });
                }
            };
        }]);


})(window, window.angular);


