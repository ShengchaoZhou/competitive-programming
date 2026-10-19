import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

// Run from any directory: node scripts/validate-algorithm-notes.mjs
const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const notesRoot = path.join(root, 'document', '算法笔记');
const manifest = JSON.parse(fs.readFileSync(path.join(notesRoot, '题集数据.json'), 'utf8').replace(/^\uFEFF/, ''));
const issues = [];
const fail = message => issues.push(message);
const relative = file => path.relative(root, file).replaceAll('\\', '/');
const label = problem => `${problem.platform} ${problem.id}. ${problem.title.trim()}`;
const identity = (title, url) => `${title}\n${url}`;
const byReference = new Map();
const byKey = new Map();
for (const problem of manifest.problems) {
  if (byKey.has(problem.key)) fail(`重复题目 key: ${problem.key}`);
  byKey.set(problem.key, problem);
  const ref = identity(label(problem), problem.url);
  if (byReference.has(ref)) fail(`重复题目引用: ${label(problem)}`);
  byReference.set(ref, problem);
  if (new Set(problem.sources).size !== problem.sources.length) fail(`重复来源: ${problem.key}`);
}

const sourceLabels = manifest.sources.map(source => source.label);
for (const source of manifest.sources) {
  const count = manifest.problems.filter(problem => problem.sources.includes(source.label)).length;
  if (count !== source.count) fail(`${source.label}: 应为 ${source.count} 题，实际 ${count} 题`);
}
for (const problem of manifest.problems) {
  const ordered = sourceLabels.filter(source => problem.sources.includes(source));
  if (ordered.join('/') !== problem.sources.join('/')) fail(`来源名称或顺序错误: ${problem.key}`);
}
const ranks = manifest.problems.filter(problem => problem.sources.includes('CodeTop200'))
  .map(problem => problem.codeTopRank).sort((a, b) => a - b);
if (ranks.length !== 200 || ranks.some((rank, index) => rank !== index + 1)) fail('CodeTop 排名必须完整覆盖 1～200');

function walk(dir) {
  return fs.readdirSync(dir, { withFileTypes: true }).flatMap(entry => {
    const file = path.join(dir, entry.name);
    return entry.isDirectory() ? walk(file) : entry.name.endsWith('.md') ? [file] : [];
  });
}
function slug(text) {
  return text.replace(/\[([^\]]+)\]\([^)]+\)/g, '$1').replace(/<[^>]+>/g, '')
    .toLowerCase().replace(/[^\p{L}\p{N}\s_-]/gu, '').trim().replace(/\s/g, '-');
}
function anchors(text) {
  const result = new Set();
  const counts = new Map();
  for (const match of text.matchAll(/^#{1,6} (.+)$/gm)) {
    const base = slug(match[1]);
    const count = counts.get(base) || 0;
    result.add(count ? `${base}-${count}` : base);
    counts.set(base, count + 1);
  }
  for (const match of text.matchAll(/\bid=["']([^"']+)["']/g)) result.add(match[1]);
  return result;
}

const files = walk(notesRoot);
const practiceRoot = path.join(root, 'document', '题集');
const practiceFiles = fs.existsSync(practiceRoot) ? walk(practiceRoot) : [];
// Personal practice notes are checked for local links, not topic structure or primary placement.
const content = new Map([...files, ...practiceFiles].map(file => [file, fs.readFileSync(file, 'utf8').replaceAll('\r\n', '\n')]));
const placements = new Map();
const topicFiles = files.filter(file => path.basename(file) !== 'README.md');
for (const file of topicFiles) {
  const text = content.get(file);
  const name = relative(file);
  if (!text.startsWith('# ')) fail(`${name}: 缺少一级标题`);
  const bodyStart = text.search(/^## /m);
  if (bodyStart < 0) fail(`${name}: 缺少二级模型标题`);
  const header = text.slice(0, bodyStart);
  if (!/^\|[^\n]+\|$/m.test(header)) fail(`${name}: 缺少页首题目表格`);
  if (/^#{4,} /m.test(text)) fail(`${name}: 仍有三级以下的标题`);
  if (/^(?:#{1,6} )?(?:模型能解决的问题|常见题型|代表题目[：:]?|经典例题)$/m.test(text)) fail(`${name}: 包含已弃用的标题或提示语`);
  if (/^识别.*背包.*关键/m.test(text)) fail(`${name}: 包含已弃用的识别提示`);
  if (/^```(?:java|text)\s*$/m.test(text)) fail(`${name}: 仍保留完整代码或输入输出样例块`);
  if (/[ \t]+$/m.test(text)) fail(`${name}: 存在行末空白`);
  const local = new Map();
  const references = /\[((?:LeetCode|AcWing|CodeTop) [^\]]+)\]\((https?:\/\/[^)\s]+)\)(?:[ \t]*（([^）\r\n]*)）)?/g;
  for (const match of text.matchAll(references)) {
    const problem = byReference.get(identity(match[1], match[2]));
    if (!problem) {
      fail(`${name}: 未登记或名称/链接不一致: ${match[1]}`);
      continue;
    }
    if ((match[3] || '') !== problem.sources.join('/')) fail(`${name}: ${problem.key} 的题集标签不一致`);
    const placement = local.get(problem.key) || { table: 0, body: 0 };
    if (match.index < bodyStart) placement.table++;
    else placement.body++;
    local.set(problem.key, placement);
  }
  if (!local.size) fail(`${name}: 专题没有登记题目`);
  for (const [key, count] of local) {
    if (count.table !== 1 || count.body !== 1) fail(`${name}: ${key} 应在表格和正文各出现一次，实际 ${count.table}/${count.body}`);
    const locations = placements.get(key) || [];
    locations.push(name);
    placements.set(key, locations);
  }
}
for (const problem of manifest.problems) {
  const locations = placements.get(problem.key) || [];
  if (locations.length !== 1) fail(`${problem.key}: 应有一个主归属专题，实际 ${locations.length} 个 ${locations.join(', ')}`);
  if (!problem.primaryFile || locations[0] !== problem.primaryFile) fail(`${problem.key}: primaryFile 与实际归属不一致`);
}

// Check local navigation and anchors, including all module indexes and the repository entry.
const repoReadme = path.join(root, 'README.md');
content.set(repoReadme, fs.readFileSync(repoReadme, 'utf8').replaceAll('\r\n', '\n'));
for (const [file, text] of content) {
  for (const match of text.matchAll(/\[[^\]]*\]\(([^)\s]+)\)/g)) {
    const target = match[1];
    if (/^[a-z][a-z0-9+.-]*:/i.test(target) || target.startsWith('//')) continue;
    const [rawPath, fragment] = target.split('#');
    const destination = rawPath ? path.resolve(path.dirname(file), decodeURIComponent(rawPath)) : file;
    if (!fs.existsSync(destination)) {
      fail(`${relative(file)}: 本地链接不存在: ${target}`);
      continue;
    }
    if (fragment && destination.endsWith('.md')) {
      const targetText = content.get(destination) || fs.readFileSync(destination, 'utf8').replaceAll('\r\n', '\n');
      if (!anchors(targetText).has(decodeURIComponent(fragment))) fail(`${relative(file)}: 锚点不存在: ${target}`);
    }
  }
}

if (issues.length) {
  console.error(issues.join('\n'));
  process.exitCode = 1;
} else {
  const sources = Object.fromEntries(manifest.sources.map(source => [source.label, source.count]));
  console.log(JSON.stringify({ topics: topicFiles.length, problems: manifest.problems.length,
    supplementary: manifest.problems.filter(problem => !problem.sources.length).length,
    sourceCoverage: sources, uniquePrimaryFiles: true, tablesAndBodiesMatch: true,
    sourceTagsMatch: true, localLinksAndAnchorsValid: true }, null, 2));
}
