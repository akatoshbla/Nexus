/*! ng-dialog - v0.5.5 (https://github.com/likeastore/ngDialog) */
!function (a, b) {
    "undefined" != typeof module && module.exports ? "undefined" == typeof angular ? (b(require("angular")), module.exports = "ngDialog") : (b(angular), module.exports = "ngDialog") : "function" == typeof define && define.amd ? define(["ng-dialog"], function () {
        b(a.angular)
    }) : b(a.angular)
}(this, function (a) {
    "use strict";
    var b = a.module("ngDialog", []), c = a.element, d = a.isDefined, e = (document.body || document.documentElement).style, f = d(e.animation) || d(e.WebkitAnimation) || d(e.MozAnimation) || d(e.MsAnimation) || d(e.OAnimation), g = "animationend webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend", h = "a[href], area[href], input:not([disabled]), select:not([disabled]), textarea:not([disabled]), button:not([disabled]), iframe, object, embed, *[tabindex], *[contenteditable]", i = "ngdialog-disabled-animation", j = {
        html: !1,
        body: !1
    }, k = {}, l = [], m = !1;
    return b.provider("ngDialog", function () {
        var b = this.defaults = {
            className: "ngdialog-theme-default",
            disableAnimation: !1,
            plain: !1,
            showClose: !0,
            closeByDocument: !0,
            closeByEscape: !0,
            closeByNavigation: !1,
            appendTo: !1,
            preCloseCallback: !1,
            overlay: !0,
            cache: !0,
            trapFocus: !0,
            preserveFocus: !0,
            ariaAuto: !0,
            ariaRole: null,
            ariaLabelledById: null,
            ariaLabelledBySelector: null,
            ariaDescribedById: null,
            ariaDescribedBySelector: null
        };
        this.setForceHtmlReload = function (a) {
            j.html = a || !1
        }, this.setForceBodyReload = function (a) {
            j.body = a || !1
        }, this.setDefaults = function (c) {
            a.extend(b, c)
        };
        var d, e = 0, n = 0, o = {};
        this.$get = ["$document", "$templateCache", "$compile", "$q", "$http", "$rootScope", "$timeout", "$window", "$controller", "$injector", function (p, q, r, s, t, u, v, w, x, y) {
            var z = [], A = {
                onDocumentKeydown: function (a) {
                    27 === a.keyCode && B.close("$escape")
                }, activate: function (a) {
                    var b = a.data("$ngDialogOptions");
                    b.trapFocus && (a.on("keydown", A.onTrapFocusKeydown), z.body.on("keydown", A.onTrapFocusKeydown))
                }, deactivate: function (a) {
                    a.off("keydown", A.onTrapFocusKeydown), z.body.off("keydown", A.onTrapFocusKeydown)
                }, deactivateAll: function (b) {
                    a.forEach(b, function (b) {
                        var c = a.element(b);
                        A.deactivate(c)
                    })
                }, setBodyPadding: function (a) {
                    var b = parseInt(z.body.css("padding-right") || 0, 10);
                    z.body.css("padding-right", b + a + "px"), z.body.data("ng-dialog-original-padding", b), u.$broadcast("ngDialog.setPadding", a)
                }, resetBodyPadding: function () {
                    var a = z.body.data("ng-dialog-original-padding");
                    a ? z.body.css("padding-right", a + "px") : z.body.css("padding-right", ""), u.$broadcast("ngDialog.setPadding", 0)
                }, performCloseDialog: function (a, b) {
                    var c = a.data("$ngDialogOptions"), e = a.attr("id"), h = k[e];
                    if (h) {
                        if ("undefined" != typeof w.Hammer) {
                            var i = h.hammerTime;
                            i.off("tap", d), i.destroy && i.destroy(), delete h.hammerTime
                        } else a.unbind("click");
                        1 === n && z.body.unbind("keydown", A.onDocumentKeydown), a.hasClass("ngdialog-closing") || (n -= 1);
                        var j = a.data("$ngDialogPreviousFocus");
                        j && j.focus && j.focus(), u.$broadcast("ngDialog.closing", a, b), n = 0 > n ? 0 : n, f && !c.disableAnimation ? (h.$destroy(), a.unbind(g).bind(g, function () {
                            A.closeDialogElement(a, b)
                        }).addClass("ngdialog-closing")) : (h.$destroy(), A.closeDialogElement(a, b)), o[e] && (o[e].resolve({
                            id: e,
                            value: b,
                            $dialog: a,
                            remainingDialogs: n
                        }), delete o[e]), k[e] && delete k[e], l.splice(l.indexOf(e), 1), l.length || (z.body.unbind("keydown", A.onDocumentKeydown), m = !1)
                    }
                }, closeDialogElement: function (a, b) {
                    a.remove(), 0 === n && (z.html.removeClass("ngdialog-open"), z.body.removeClass("ngdialog-open"), A.resetBodyPadding()), u.$broadcast("ngDialog.closed", a, b)
                }, closeDialog: function (b, c) {
                    var d = b.data("$ngDialogPreCloseCallback");
                    if (d && a.isFunction(d)) {
                        var e = d.call(b, c);
                        a.isObject(e) ? e.closePromise ? e.closePromise.then(function () {
                            A.performCloseDialog(b, c)
                        }) : e.then(function () {
                            A.performCloseDialog(b, c)
                        }, function () {
                        }) : e !== !1 && A.performCloseDialog(b, c)
                    } else A.performCloseDialog(b, c)
                }, onTrapFocusKeydown: function (b) {
                    var c, d = a.element(b.currentTarget);
                    if (d.hasClass("ngdialog"))c = d; else if (c = A.getActiveDialog(), null === c)return;
                    var e = 9 === b.keyCode, f = b.shiftKey === !0;
                    e && A.handleTab(c, b, f)
                }, handleTab: function (a, b, c) {
                    var d = A.getFocusableElements(a);
                    if (0 === d.length)return void(document.activeElement && document.activeElement.blur());
                    var e = document.activeElement, f = Array.prototype.indexOf.call(d, e), g = -1 === f, h = 0 === f, i = f === d.length - 1, j = !1;
                    c ? (g || h) && (d[d.length - 1].focus(), j = !0) : (g || i) && (d[0].focus(), j = !0), j && (b.preventDefault(), b.stopPropagation())
                }, autoFocus: function (a) {
                    var b = a[0], d = b.querySelector("*[autofocus]");
                    if (null === d || (d.focus(), document.activeElement !== d)) {
                        var e = A.getFocusableElements(a);
                        if (e.length > 0)return void e[0].focus();
                        var f = A.filterVisibleElements(b.querySelectorAll("h1,h2,h3,h4,h5,h6,p,span"));
                        if (f.length > 0) {
                            var g = f[0];
                            c(g).attr("tabindex", "-1").css("outline", "0"), g.focus()
                        }
                    }
                }, getFocusableElements: function (a) {
                    var b = a[0], c = b.querySelectorAll(h), d = A.filterTabbableElements(c);
                    return A.filterVisibleElements(d)
                }, filterTabbableElements: function (a) {
                    for (var b = [], d = 0; d < a.length; d++) {
                        var e = a[d];
                        "-1" !== c(e).attr("tabindex") && b.push(e)
                    }
                    return b
                }, filterVisibleElements: function (a) {
                    for (var b = [], c = 0; c < a.length; c++) {
                        var d = a[c];
                        (d.offsetWidth > 0 || d.offsetHeight > 0) && b.push(d)
                    }
                    return b
                }, getActiveDialog: function () {
                    var a = document.querySelectorAll(".ngdialog");
                    return 0 === a.length ? null : c(a[a.length - 1])
                }, applyAriaAttributes: function (a, b) {
                    if (b.ariaAuto) {
                        if (!b.ariaRole) {
                            var c = A.getFocusableElements(a).length > 0 ? "dialog" : "alertdialog";
                            b.ariaRole = c
                        }
                        b.ariaLabelledBySelector || (b.ariaLabelledBySelector = "h1,h2,h3,h4,h5,h6"), b.ariaDescribedBySelector || (b.ariaDescribedBySelector = "article,section,p")
                    }
                    b.ariaRole && a.attr("role", b.ariaRole), A.applyAriaAttribute(a, "aria-labelledby", b.ariaLabelledById, b.ariaLabelledBySelector), A.applyAriaAttribute(a, "aria-describedby", b.ariaDescribedById, b.ariaDescribedBySelector)
                }, applyAriaAttribute: function (a, b, d, e) {
                    if (d && a.attr(b, d), e) {
                        var f = a.attr("id"), g = a[0].querySelector(e);
                        if (!g)return;
                        var h = f + "-" + b;
                        return c(g).attr("id", h), a.attr(b, h), h
                    }
                }, detectUIRouter: function () {
                    try {
                        return a.module("ui.router"), !0
                    } catch (b) {
                        return !1
                    }
                }, getRouterLocationEventName: function () {
                    return A.detectUIRouter() ? "$stateChangeSuccess" : "$locationChangeSuccess"
                }
            }, B = {
                open: function (f) {
                    function g(a, b) {
                        return u.$broadcast("ngDialog.templateLoading", a), t.get(a, b || {}).then(function (b) {
                            return u.$broadcast("ngDialog.templateLoaded", a), b.data || ""
                        })
                    }

                    function h(b) {
                        return b ? a.isString(b) && j.plain ? b : "boolean" != typeof j.cache || j.cache ? g(b, {cache: q}) : g(b, {cache: !1}) : "Empty template"
                    }

                    var j = a.copy(b), p = ++e, C = "ngdialog" + p;
                    l.push(C), f = f || {}, a.extend(j, f);
                    var D;
                    o[C] = D = s.defer();
                    var E;
                    k[C] = E = a.isObject(j.scope) ? j.scope.$new() : u.$new();
                    var F, G, H = a.extend({}, j.resolve);
                    return a.forEach(H, function (b, c) {
                        H[c] = a.isString(b) ? y.get(b) : y.invoke(b, null, null, c)
                    }), s.all({template: h(j.template || j.templateUrl), locals: s.all(H)}).then(function (b) {
                        var e = b.template, f = b.locals;
                        j.showClose && (e += '<div class="ngdialog-close"></div>');
                        var g = j.overlay ? "" : " ngdialog-no-overlay";
                        if (F = c('<div id="ngdialog' + p + '" class="ngdialog' + g + '"></div>'), F.html(j.overlay ? '<div class="ngdialog-overlay"></div><div class="ngdialog-content" role="document">' + e + "</div>" : '<div class="ngdialog-content" role="document">' + e + "</div>"), F.data("$ngDialogOptions", j), E.ngDialogId = C, j.data && a.isString(j.data)) {
                            var h = j.data.replace(/^\s*/, "")[0];
                            E.ngDialogData = "{" === h || "[" === h ? a.fromJson(j.data) : j.data, E.ngDialogData.ngDialogId = C
                        } else j.data && a.isObject(j.data) && (E.ngDialogData = j.data, E.ngDialogData.ngDialogId = C);
                        if (j.className && F.addClass(j.className), j.disableAnimation && F.addClass(i), G = j.appendTo && a.isString(j.appendTo) ? a.element(document.querySelector(j.appendTo)) : z.body, A.applyAriaAttributes(F, j), j.preCloseCallback) {
                            var k;
                            a.isFunction(j.preCloseCallback) ? k = j.preCloseCallback : a.isString(j.preCloseCallback) && E && (a.isFunction(E[j.preCloseCallback]) ? k = E[j.preCloseCallback] : E.$parent && a.isFunction(E.$parent[j.preCloseCallback]) ? k = E.$parent[j.preCloseCallback] : u && a.isFunction(u[j.preCloseCallback]) && (k = u[j.preCloseCallback])), k && F.data("$ngDialogPreCloseCallback", k)
                        }
                        if (E.closeThisDialog = function (a) {
                                A.closeDialog(F, a)
                            }, j.controller && (a.isString(j.controller) || a.isArray(j.controller) || a.isFunction(j.controller))) {
                            var l;
                            j.controllerAs && a.isString(j.controllerAs) && (l = j.controllerAs);
                            var o = x(j.controller, a.extend(f, {$scope: E, $element: F}), null, l);
                            F.data("$ngDialogControllerController", o)
                        }
                        if (v(function () {
                                var a = document.querySelectorAll(".ngdialog");
                                A.deactivateAll(a), r(F)(E);
                                var b = w.innerWidth - z.body.prop("clientWidth");
                                z.html.addClass("ngdialog-open"), z.body.addClass("ngdialog-open");
                                var c = b - (w.innerWidth - z.body.prop("clientWidth"));
                                c > 0 && A.setBodyPadding(c), G.append(F), A.activate(F), j.trapFocus && A.autoFocus(F), j.name ? u.$broadcast("ngDialog.opened", {
                                    dialog: F,
                                    name: j.name
                                }) : u.$broadcast("ngDialog.opened", F)
                            }), m || (z.body.bind("keydown", A.onDocumentKeydown), m = !0), j.closeByNavigation) {
                            var q = A.getRouterLocationEventName();
                            u.$on(q, function () {
                                A.closeDialog(F)
                            })
                        }
                        if (j.preserveFocus && F.data("$ngDialogPreviousFocus", document.activeElement), d = function (a) {
                                var b = j.closeByDocument ? c(a.target).hasClass("ngdialog-overlay") : !1, d = c(a.target).hasClass("ngdialog-close");
                                (b || d) && B.close(F.attr("id"), d ? "$closeButton" : "$document")
                            }, "undefined" != typeof w.Hammer) {
                            var s = E.hammerTime = w.Hammer(F[0]);
                            s.on("tap", d)
                        } else F.bind("click", d);
                        return n += 1, B
                    }), {
                        id: C, closePromise: D.promise, close: function (a) {
                            A.closeDialog(F, a)
                        }
                    }
                }, openConfirm: function (b) {
                    var d = s.defer(), e = {closeByEscape: !1, closeByDocument: !1};
                    a.extend(e, b), e.scope = a.isObject(e.scope) ? e.scope.$new() : u.$new(), e.scope.confirm = function (a) {
                        d.resolve(a);
                        var b = c(document.getElementById(f.id));
                        A.performCloseDialog(b, a)
                    };
                    var f = B.open(e);
                    return f.closePromise.then(function (a) {
                        return a ? d.reject(a.value) : d.reject()
                    }), d.promise
                }, isOpen: function (a) {
                    var b = c(document.getElementById(a));
                    return b.length > 0
                }, close: function (a, b) {
                    var d = c(document.getElementById(a));
                    if (d.length)A.closeDialog(d, b); else if ("$escape" === a) {
                        var e = l[l.length - 1];
                        d = c(document.getElementById(e)), d.data("$ngDialogOptions").closeByEscape && A.closeDialog(d, "$escape")
                    } else B.closeAll(b);
                    return B
                }, closeAll: function (a) {
                    for (var b = document.querySelectorAll(".ngdialog"), d = b.length - 1; d >= 0; d--) {
                        var e = b[d];
                        A.closeDialog(c(e), a)
                    }
                }, getOpenDialogs: function () {
                    return l
                }, getDefaults: function () {
                    return b
                }
            };
            return a.forEach(["html", "body"], function (a) {
                if (z[a] = p.find(a), j[a]) {
                    var b = A.getRouterLocationEventName();
                    u.$on(b, function () {
                        z[a] = p.find(a)
                    })
                }
            }), B
        }]
    }), b.directive("ngDialog", ["ngDialog", function (b) {
        return {
            restrict: "A", scope: {ngDialogScope: "="}, link: function (c, d, e) {
                d.on("click", function (d) {
                    d.preventDefault();
                    var f = a.isDefined(c.ngDialogScope) ? c.ngDialogScope : "noScope";
                    a.isDefined(e.ngDialogClosePrevious) && b.close(e.ngDialogClosePrevious);
                    var g = b.getDefaults();
                    b.open({
                        template: e.ngDialog,
                        className: e.ngDialogClass || g.className,
                        controller: e.ngDialogController,
                        controllerAs: e.ngDialogControllerAs,
                        bindToController: e.ngDialogBindToController,
                        scope: f,
                        data: e.ngDialogData,
                        showClose: "false" === e.ngDialogShowClose ? !1 : "true" === e.ngDialogShowClose ? !0 : g.showClose,
                        closeByDocument: "false" === e.ngDialogCloseByDocument ? !1 : "true" === e.ngDialogCloseByDocument ? !0 : g.closeByDocument,
                        closeByEscape: "false" === e.ngDialogCloseByEscape ? !1 : "true" === e.ngDialogCloseByEscape ? !0 : g.closeByEscape,
                        overlay: "false" === e.ngDialogOverlay ? !1 : "true" === e.ngDialogOverlay ? !0 : g.overlay,
                        preCloseCallback: e.ngDialogPreCloseCallback || g.preCloseCallback
                    })
                })
            }
        }
    }]), b
});