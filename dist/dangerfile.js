"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
Object.defineProperty(exports, "__esModule", { value: true });
var danger_1 = require("danger");
function run() {
    return __awaiter(this, void 0, void 0, function () {
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0: 
                // Warnings
                return [4 /*yield*/, checkPrTitleIsFormattedCorrectly()];
                case 1:
                    // Warnings
                    _a.sent();
                    return [4 /*yield*/, checkPrBodyReferencesAtLeastOneIssue()];
                case 2:
                    _a.sent();
                    return [4 /*yield*/, checkAddedAtLeastOneChangelogFile()];
                case 3:
                    _a.sent();
                    return [4 /*yield*/, checkConsoleLogUsage()
                        // Messages
                    ];
                case 4:
                    _a.sent();
                    // Messages
                    return [4 /*yield*/, encourageSmallerPullRequestDiffs()];
                case 5:
                    // Messages
                    _a.sent();
                    return [4 /*yield*/, encourageVerifyingBreakingChanges()];
                case 6:
                    _a.sent();
                    return [4 /*yield*/, encourageVerifyingDependencyLicences()];
                case 7:
                    _a.sent();
                    return [4 /*yield*/, encourageVerifyingFrontendDependencySizes()];
                case 8:
                    _a.sent();
                    return [4 /*yield*/, encourageVerifyingTodoComments()];
                case 9:
                    _a.sent();
                    return [2 /*return*/];
            }
        });
    });
}
function checkPrTitleIsFormattedCorrectly() {
    return __awaiter(this, void 0, void 0, function () {
        var titleStartsWithPrefix, titleIsDefault;
        return __generator(this, function (_a) {
            titleStartsWithPrefix = danger_1.danger.github.pr.title.startsWith('[PR] ');
            titleIsDefault = danger_1.danger.github.pr.title.includes(danger_1.danger.github.pr.head.ref);
            if (!titleStartsWithPrefix || titleIsDefault) {
                danger_1.warn('The title of this PR is not formatted correctly. ' +
                    'Please use the format of `[PR] My describing title`.');
            }
            return [2 /*return*/];
        });
    });
}
function checkPrBodyReferencesAtLeastOneIssue() {
    return __awaiter(this, void 0, void 0, function () {
        var bodyIncludesIssue;
        return __generator(this, function (_a) {
            bodyIncludesIssue = danger_1.danger.github.pr.body.match(/(References|Closes) #\d/);
            if (!bodyIncludesIssue) {
                danger_1.warn('The body of this PR does not reference any issues. ' +
                    'Please use the format of `References #1234` if your PR is part of an issue, ' +
                    'and the format of `Closes #1234` if your PR closes an issue. ' +
                    'When referencing or closing multiple issues, please use a markdown list.');
            }
            return [2 /*return*/];
        });
    });
}
function checkAddedAtLeastOneChangelogFile() {
    return __awaiter(this, void 0, void 0, function () {
        var createdChangelogFiles;
        return __generator(this, function (_a) {
            createdChangelogFiles = danger_1.danger.git.created_files
                .filter(function (path) { return path.includes('_UNRELEASED_CHANGES/') && path.includes('.json'); });
            if (createdChangelogFiles.length === 0) {
                danger_1.warn('This PR does not create a file in `_UNRELEASED_CHANGES`. ' +
                    'Please add a file describing the changes that this PR is making.');
            }
            return [2 /*return*/];
        });
    });
}
function checkConsoleLogUsage() {
    return __awaiter(this, void 0, void 0, function () {
        var additionsByFile, consoleLogFiles;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0: return [4 /*yield*/, getAdditionsByFile()];
                case 1:
                    additionsByFile = _a.sent();
                    consoleLogFiles = Object.keys(additionsByFile)
                        .filter(function (file) { return additionsByFile[file].match(/console\.log/); });
                    if (consoleLogFiles.length > 0) {
                        danger_1.message('This PR seems to add one or more `console.log` statements. ' +
                            'Please make sure to remove them if they are not intended to be there.' +
                            '\n\n' +
                            consoleLogFiles.map(function (file) { return "- `" + file + "`"; }).join('\n'));
                    }
                    return [2 /*return*/];
            }
        });
    });
}
function encourageSmallerPullRequestDiffs() {
    return __awaiter(this, void 0, void 0, function () {
        var totalLinesChanged, totalFilesChanged;
        return __generator(this, function (_a) {
            totalLinesChanged = danger_1.danger.github.pr.additions + danger_1.danger.github.pr.deletions;
            totalFilesChanged = danger_1.danger.git.created_files.length + danger_1.danger.git.modified_files.length + danger_1.danger.git.deleted_files.length;
            if (totalLinesChanged > 5000 || totalFilesChanged > 100) {
                danger_1.message('This PR\'s diff seems pretty big. If it contains changes in multiple areas, ' +
                    'please see if you can split it into separate PRs for easier and faster reviews.');
            }
            return [2 /*return*/];
        });
    });
}
function encourageVerifyingBreakingChanges() {
    return __awaiter(this, void 0, void 0, function () {
        var changedFiles, changedSchemaFiles, changedMigrationFiles;
        return __generator(this, function (_a) {
            changedFiles = [].concat(danger_1.danger.git.created_files, danger_1.danger.git.modified_files, danger_1.danger.git.deleted_files);
            changedSchemaFiles = changedFiles.filter(function (path) { return path.includes('api-v2') && path.includes('_schema.js'); });
            changedMigrationFiles = changedFiles.filter(function (path) { return path.includes('api-internal') && path.includes('migrations'); });
            if (changedSchemaFiles.length > 0 || changedMigrationFiles.length > 0) {
                danger_1.message('This PR looks like it changes the schema of the public API or the database. ' +
                    'Please verify if there are any breaking changes. ' +
                    'If breaking changes are intended, please make sure to add an entry to `MIGRATION.md`.');
            }
            return [2 /*return*/];
        });
    });
}
function encourageVerifyingDependencyLicences() {
    return __awaiter(this, void 0, void 0, function () {
        var changedFiles, changedPackageFiles;
        return __generator(this, function (_a) {
            changedFiles = [].concat(danger_1.danger.git.created_files, danger_1.danger.git.modified_files);
            changedPackageFiles = changedFiles.filter(function (path) { return path.includes('package.json'); });
            if (changedPackageFiles.length > 0) {
                danger_1.message('This PR looks like it changes the dependencies of one or more micro-services. ' +
                    'Please verify if the licences of the dependencies match [our guidelines](https://git.io/fhrev).');
            }
            return [2 /*return*/];
        });
    });
}
function encourageVerifyingFrontendDependencySizes() {
    return __awaiter(this, void 0, void 0, function () {
        var changedFrontendPackageFiles;
        return __generator(this, function (_a) {
            changedFrontendPackageFiles = danger_1.danger.git.modified_files
                .filter(function (path) { return path.includes('frontend/package.json'); });
            if (changedFrontendPackageFiles.length > 0) {
                danger_1.message('This PR looks like it changes the dependencies of the frontend. ' +
                    'Please verify if the [size of the dependencies](https://bundlephobia.com/) is reasonable.');
            }
            return [2 /*return*/];
        });
    });
}
function encourageVerifyingTodoComments() {
    return __awaiter(this, void 0, void 0, function () {
        var additionsByFile, todoCommentFiles;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0: return [4 /*yield*/, getAdditionsByFile()];
                case 1:
                    additionsByFile = _a.sent();
                    todoCommentFiles = Object.keys(additionsByFile)
                        .filter(function (file) { return additionsByFile[file].match(/\/\/ ?TODO/i); });
                    if (todoCommentFiles.length > 0) {
                        danger_1.message('This PR seems to add one or more TODO comments. ' +
                            'Please verify if they are intended to be in the code, or if functionality is missing.' +
                            '\n\n' +
                            todoCommentFiles.map(function (file) { return "- `" + file + "`"; }).join('\n'));
                    }
                    return [2 /*return*/];
            }
        });
    });
}
function getAdditionsByFile() {
    return __awaiter(this, void 0, void 0, function () {
        var changedFiles, diffForFiles, additionsByFile;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    changedFiles = [].concat(danger_1.danger.git.created_files, danger_1.danger.git.modified_files);
                    return [4 /*yield*/, Promise.all(changedFiles.map(function (path) { return danger_1.danger.git.diffForFile(path); }))];
                case 1:
                    diffForFiles = _a.sent();
                    additionsByFile = {};
                    diffForFiles.forEach(function (diffForFile, index) {
                        var additions = diffForFile.diff
                            .split('\n')
                            .filter(function (line) { return line.startsWith('+'); })
                            .join('\n');
                        additionsByFile[changedFiles[index]] = additions;
                    });
                    return [2 /*return*/, additionsByFile];
            }
        });
    });
}
run();
//# sourceMappingURL=dangerfile.js.map