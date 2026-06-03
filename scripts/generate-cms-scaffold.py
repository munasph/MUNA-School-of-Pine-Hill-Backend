#!/usr/bin/env python3
"""Generate CMS scaffold Java files (entities, repos, DTOs, services, controllers)."""
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1] / "src/main/java/com/bezkoder/spring/jpa/postgresql"

RESOURCES = [
    {
        "name": "Event",
        "table": "events",
        "path": "events",
        "public": True,
        "fields": [
            ("title", "String", True, 300),
            ("description", "String", False, None),
            ("location", "String", False, 300),
            ("startAt", "Instant", True, None, "start_at"),
            ("endAt", "Instant", False, None, "end_at"),
            ("allDay", "boolean", False, None, "all_day"),
            ("status", "CmsPublishStatus", True, 20),
        ],
    },
    {
        "name": "NewsPost",
        "table": "news_posts",
        "path": "news",
        "public": True,
        "fields": [
            ("slug", "String", True, 200),
            ("title", "String", True, 300),
            ("summary", "String", False, 500),
            ("body", "String", False, None),
            ("imageUrl", "String", False, 500, "image_url"),
            ("author", "String", False, 200),
            ("status", "CmsPublishStatus", True, 20),
            ("publishedAt", "Instant", False, None, "published_at"),
        ],
    },
    {
        "name": "FacultyMember",
        "table": "faculty_members",
        "path": "faculty",
        "public": True,
        "fields": [
            ("name", "String", True, 200),
            ("roleTitle", "String", False, 200, "role_title"),
            ("department", "String", False, 200),
            ("email", "String", False, 200),
            ("phone", "String", False, 50),
            ("bio", "String", False, None),
            ("imageUrl", "String", False, 500, "image_url"),
            ("sortOrder", "int", False, None, "sort_order"),
            ("status", "CmsPublishStatus", True, 20),
        ],
    },
    {
        "name": "Document",
        "table": "documents",
        "path": "documents",
        "public": True,
        "fields": [
            ("title", "String", True, 300),
            ("description", "String", False, 500),
            ("category", "String", False, 100),
            ("fileUrl", "String", True, 500, "file_url"),
            ("fileName", "String", False, 300, "file_name"),
            ("sortOrder", "int", False, None, "sort_order"),
            ("status", "CmsPublishStatus", True, 20),
        ],
    },
    {
        "name": "GalleryItem",
        "table": "gallery_items",
        "path": "gallery",
        "public": True,
        "fields": [
            ("title", "String", True, 300),
            ("caption", "String", False, 500),
            ("imageUrl", "String", True, 500, "image_url"),
            ("album", "String", False, 100),
            ("sortOrder", "int", False, None, "sort_order"),
            ("status", "CmsPublishStatus", True, 20),
        ],
    },
    {
        "name": "FaqItem",
        "table": "faq_items",
        "path": "faqs",
        "public": True,
        "fields": [
            ("question", "String", True, 500),
            ("answer", "String", True, None),
            ("category", "String", False, 100),
            ("sortOrder", "int", False, None, "sort_order"),
            ("status", "CmsPublishStatus", True, 20),
        ],
    },
    {
        "name": "MediaAsset",
        "table": "media_assets",
        "path": "media",
        "public": False,
        "timestamps": False,
        "fields": [
            ("fileName", "String", True, 300, "file_name"),
            ("url", "String", True, 500),
            ("mimeType", "String", False, 100, "mime_type"),
            ("sizeBytes", "Long", False, None, "size_bytes"),
            ("altText", "String", False, 300, "alt_text"),
        ],
    },
    {
        "name": "PageSeo",
        "table": "page_seo",
        "path": "seo",
        "public": True,
        "timestamps": "updated_only",
        "fields": [
            ("pageKey", "String", True, 100, "page_key"),
            ("title", "String", False, 300),
            ("description", "String", False, 500),
            ("ogImageUrl", "String", False, 500, "og_image_url"),
        ],
    },
    {
        "name": "InquiryReplyTemplate",
        "table": "inquiry_reply_templates",
        "path": "inquiry-templates",
        "public": False,
        "fields": [
            ("name", "String", True, 200),
            ("subject", "String", True, 300),
            ("body", "String", True, None),
        ],
    },
    {
        "name": "EmailCampaign",
        "table": "email_campaigns",
        "path": "email-campaigns",
        "public": False,
        "fields": [
            ("subject", "String", True, 300),
            ("body", "String", True, None),
            ("status", "CmsPublishStatus", True, 20),
            ("sentAt", "Instant", False, None, "sent_at"),
        ],
    },
    {
        "name": "GradeIntakeLimit",
        "table": "grade_intake_limits",
        "path": "intake-limits",
        "public": False,
        "fields": [
            ("gradeKey", "String", True, 100, "grade_key"),
            ("academicYear", "String", True, 20, "academic_year"),
            ("maxApplications", "Integer", False, None, "max_applications"),
            ("waitlistEnabled", "boolean", False, None, "waitlist_enabled"),
        ],
    },
    {
        "name": "AdmissionFormField",
        "table": "admission_form_fields",
        "path": "form-fields",
        "public": True,
        "timestamps": False,
        "fields": [
            ("fieldKey", "String", True, 100, "field_key"),
            ("label", "String", True, 200),
            ("required", "boolean", False, None),
            ("active", "boolean", False, None),
            ("sortOrder", "int", False, None, "sort_order"),
        ],
    },
]


def col(field):
    return field[4] if len(field) > 4 else camel_to_snake(field[0])


def camel_to_snake(name: str) -> str:
    out = []
    for i, c in enumerate(name):
        if c.isupper() and i > 0:
            out.append("_")
        out.append(c.lower())
    return "".join(out)


def java_type(t: str) -> str:
    if t == "int":
        return "int"
    return t


def gen_entity(r):
    lines = [
        "package com.bezkoder.spring.jpa.postgresql.entity;",
        "",
        "import java.time.Instant;",
        "",
        "import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;",
        "",
        "import jakarta.persistence.Column;",
        "import jakarta.persistence.Entity;",
        "import jakarta.persistence.EnumType;",
        "import jakarta.persistence.Enumerated;",
        "import jakarta.persistence.GeneratedValue;",
        "import jakarta.persistence.GenerationType;",
        "import jakarta.persistence.Id;",
        "import jakarta.persistence.PrePersist;",
        "import jakarta.persistence.PreUpdate;",
        "import jakarta.persistence.Table;",
        "",
        f"/** Scaffold entity — `{r['table']}` table. */",
        "@Entity",
        f'@Table(name = "{r["table"]}")',
        f"public class {r['name']} {{",
        "",
        "\t@Id",
        "\t@GeneratedValue(strategy = GenerationType.IDENTITY)",
        "\tprivate Long id;",
        "",
    ]
    has_status = any(f[0] == "status" for f in r["fields"])
    for f in r["fields"]:
        col_name = col(f)
        jtype = f[1]
        nullable = not f[2]
        length = f[3] if len(f) > 3 and f[3] else None
        col_args = f'@Column(name = "{col_name}"'
        if length:
            col_args += f", length = {length}"
        if jtype == "String" and f[2]:
            col_args += ", nullable = false"
        elif nullable is False and jtype != "String":
            col_args += ", nullable = false"
        if jtype == "String" and not f[2] and f[0] == "answer":
            col_args += ', columnDefinition = "TEXT"'
        elif jtype == "String" and not f[2] and f[0] in ("description", "body", "bio"):
            col_args += ', columnDefinition = "TEXT"'
        col_args += ")"
        if jtype == "CmsPublishStatus":
            lines.append("\t@Enumerated(EnumType.STRING)")
            lines.append(col_args)
            lines.append(f"\tprivate CmsPublishStatus {f[0]} = CmsPublishStatus.DRAFT;")
        elif jtype == "boolean":
            lines.append(col_args)
            default = "false" if f[0] != "active" else "true"
            lines.append(f"\tprivate boolean {f[0]} = {default};")
        elif jtype == "int":
            lines.append(col_args)
            lines.append(f"\tprivate int {f[0]};")
        else:
            lines.append(col_args)
            lines.append(f"\tprivate {jtype} {f[0]};")
        lines.append("")
    ts = r.get("timestamps", True)
    if ts is True:
        lines += [
            "\t@Column(name = \"created_at\", nullable = false)",
            "\tprivate Instant createdAt;",
            "",
            "\t@Column(name = \"updated_at\", nullable = false)",
            "\tprivate Instant updatedAt;",
            "",
            "\t@PrePersist",
            "\tvoid onCreate() {",
            "\t\tInstant now = Instant.now();",
            "\t\tcreatedAt = now;",
            "\t\tupdatedAt = now;",
            "\t}",
            "",
            "\t@PreUpdate",
            "\tvoid onUpdate() {",
            "\t\tupdatedAt = Instant.now();",
            "\t}",
            "",
        ]
    elif ts == "updated_only":
        lines += [
            "\t@Column(name = \"updated_at\", nullable = false)",
            "\tprivate Instant updatedAt;",
            "",
            "\t@PrePersist",
            "\tvoid onCreate() {",
            "\t\tupdatedAt = Instant.now();",
            "\t}",
            "",
            "\t@PreUpdate",
            "\tvoid onUpdate() {",
            "\t\tupdatedAt = Instant.now();",
            "\t}",
            "",
        ]
    # getters/setters
    lines.append("\tpublic Long getId() { return id; }")
    lines.append("\tpublic void setId(Long id) { this.id = id; }")
    for f in r["fields"]:
        fn = f[0]
        jtype = java_type(f[1])
        lines.append(f"\tpublic {jtype} get{fn[0].upper()}{fn[1:]}() {{ return {fn}; }}")
        lines.append(f"\tpublic void set{fn[0].upper()}{fn[1:]}({jtype} {fn}) {{ this.{fn} = {fn}; }}")
    if ts:
        if ts is True:
            lines.append("\tpublic Instant getCreatedAt() { return createdAt; }")
            lines.append("\tpublic void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }")
        lines.append("\tpublic Instant getUpdatedAt() { return updatedAt; }")
        lines.append("\tpublic void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }")
    lines.append("}")
    return "\n".join(lines)


def gen_dto_request(r):
    pkg = dto_pkg(r)
    lines = [
        f"package com.bezkoder.spring.jpa.postgresql.dto.{pkg};",
        "",
    ]
    if any(f[1] == "CmsPublishStatus" for f in r["fields"]):
        lines.append("import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;")
        lines.append("")
    lines.append("import jakarta.validation.constraints.NotBlank;")
    lines.append("import jakarta.validation.constraints.Size;")
    lines.append("")
    lines.append(f"public class {r['name']}Request {{")
    lines.append("")
    for f in r["fields"]:
        fn = f[0]
        if f[2] and f[1] == "String":
            lines.append("\t@NotBlank")
            if f[3]:
                lines.append(f"\t@Size(max = {f[3]})")
        lines.append(f"\tprivate {java_type(f[1])} {fn};")
        lines.append("")
    for f in r["fields"]:
        fn = f[0]
        jtype = java_type(f[1])
        lines.append(f"\tpublic {jtype} get{fn[0].upper()}{fn[1:]}() {{ return {fn}; }}")
        lines.append(f"\tpublic void set{fn[0].upper()}{fn[1:]}({jtype} {fn}) {{ this.{fn} = {fn}; }}")
    lines.append("}")
    return "\n".join(lines)


def dto_pkg(r):
    key = r["name"]
    mapping = {
        "NewsPost": "news",
        "FacultyMember": "faculty",
        "GalleryItem": "gallery",
        "FaqItem": "faq",
        "MediaAsset": "media",
        "PageSeo": "seo",
        "InquiryReplyTemplate": "inquiry",
        "EmailCampaign": "campaign",
        "GradeIntakeLimit": "intake",
        "AdmissionFormField": "formfield",
        "Event": "event",
        "Document": "document",
    }
    return mapping.get(key, key.lower())


def gen_dto_response(r):
    pkg = dto_pkg(r)
    lines = [
        f"package com.bezkoder.spring.jpa.postgresql.dto.{pkg};",
        "",
        "import java.time.Instant;",
        "",
    ]
    if any(f[1] == "CmsPublishStatus" for f in r["fields"]):
        lines.append("import com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;")
        lines.append("")
    lines.append(f"public class {r['name']}Response {{")
    lines.append("")
    lines.append("\tprivate Long id;")
    for f in r["fields"]:
        lines.append(f"\tprivate {java_type(f[1])} {f[0]};")
    ts = r.get("timestamps", True)
    if ts is True:
        lines.append("\tprivate Instant createdAt;")
        lines.append("\tprivate Instant updatedAt;")
    elif ts == "updated_only":
        lines.append("\tprivate Instant updatedAt;")
    lines.append("")
    lines.append("\tpublic Long getId() { return id; }")
    lines.append("\tpublic void setId(Long id) { this.id = id; }")
    for f in r["fields"]:
        fn = f[0]
        jtype = java_type(f[1])
        lines.append(f"\tpublic {jtype} get{fn[0].upper()}{fn[1:]}() {{ return {fn}; }}")
        lines.append(f"\tpublic void set{fn[0].upper()}{fn[1:]}({jtype} {fn}) {{ this.{fn} = {fn}; }}")
    if ts:
        if ts is True:
            lines.append("\tpublic Instant getCreatedAt() { return createdAt; }")
            lines.append("\tpublic void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }")
        lines.append("\tpublic Instant getUpdatedAt() { return updatedAt; }")
        lines.append("\tpublic void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }")
    lines.append("}")
    return "\n".join(lines)


def gen_repository(r):
    order = "createdAt" if r.get("timestamps", True) is True else "id"
    return f"""package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.jpa.postgresql.entity.{r['name']};

@Repository
public interface {r['name']}Repository extends JpaRepository<{r['name']}, Long> {{

\tList<{r['name']}> findAllByOrderBy{order[0].upper()}{order[1:]}Desc();
}}
"""


def gen_service_interface(r):
    pkg = dto_pkg(r)
    return f"""package com.bezkoder.spring.jpa.postgresql.service;

import java.util.List;

import com.bezkoder.spring.jpa.postgresql.dto.{pkg}.{r['name']}Request;
import com.bezkoder.spring.jpa.postgresql.dto.{pkg}.{r['name']}Response;

public interface {r['name']}Service {{

\tList<{r['name']}Response> findAll();

\tList<{r['name']}Response> findPublished();

\t{r['name']}Response findById(Long id);

\t{r['name']}Response create({r['name']}Request request);

\t{r['name']}Response update(Long id, {r['name']}Request request);

\tvoid delete(Long id);
}}
"""


def gen_service_impl(r):
    pkg = dto_pkg(r)
    has_status = any(f[0] == "status" for f in r["fields"])
    published_filter = ""
    if has_status:
        published_filter = """
\t@Override
\tpublic List<{name}Response> findPublished() {{
\t\treturn repository.findAllByOrderByCreatedAtDesc().stream()
\t\t\t\t.filter(e -> e.getStatus() == CmsPublishStatus.PUBLISHED)
\t\t\t\t.map(this::toResponse)
\t\t\t\t.toList();
\t}}
""".format(name=r["name"])
    else:
        published_filter = """
\t@Override
\tpublic List<{name}Response> findPublished() {{
\t\treturn findAll();
\t}}
""".format(name=r["name"])

    apply_lines = []
    for f in r["fields"]:
        fn = f[0]
        apply_lines.append(f"\t\tentity.set{fn[0].upper()}{fn[1:]}(request.get{fn[0].upper()}{fn[1:]}());")

    resp_lines = ["\t\tr.setId(entity.getId());"]
    for f in r["fields"]:
        fn = f[0]
        resp_lines.append(f"\t\tr.set{fn[0].upper()}{fn[1:]}(entity.get{fn[0].upper()}{fn[1:]}());")
    ts = r.get("timestamps", True)
    if ts is True:
        resp_lines.append("\t\tr.setCreatedAt(entity.getCreatedAt());")
        resp_lines.append("\t\tr.setUpdatedAt(entity.getUpdatedAt());")
    elif ts == "updated_only":
        resp_lines.append("\t\tr.setUpdatedAt(entity.getUpdatedAt());")

    imports = f"""import com.bezkoder.spring.jpa.postgresql.dto.{pkg}.{r['name']}Request;
import com.bezkoder.spring.jpa.postgresql.dto.{pkg}.{r['name']}Response;
import com.bezkoder.spring.jpa.postgresql.entity.{r['name']};
import com.bezkoder.spring.jpa.postgresql.exception.ResourceNotFoundException;
import com.bezkoder.spring.jpa.postgresql.repository.{r['name']}Repository;"""
    if has_status:
        imports += "\nimport com.bezkoder.spring.jpa.postgresql.entity.enums.CmsPublishStatus;"

    return f"""package com.bezkoder.spring.jpa.postgresql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

{imports}

@Service
@Transactional(readOnly = true)
public class {r['name']}ServiceImpl implements {r['name']}Service {{

\tprivate final {r['name']}Repository repository;

\tpublic {r['name']}ServiceImpl({r['name']}Repository repository) {{
\t\tthis.repository = repository;
\t}}

\t@Override
\tpublic List<{r['name']}Response> findAll() {{
\t\treturn repository.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
\t}}
{published_filter}
\t@Override
\tpublic {r['name']}Response findById(Long id) {{
\t\treturn toResponse(findOrThrow(id));
\t}}

\t@Override
\t@Transactional
\tpublic {r['name']}Response create({r['name']}Request request) {{
\t\t{r['name']} entity = new {r['name']}();
\t\tapplyRequest(entity, request);
\t\treturn toResponse(repository.save(entity));
\t}}

\t@Override
\t@Transactional
\tpublic {r['name']}Response update(Long id, {r['name']}Request request) {{
\t\t{r['name']} entity = findOrThrow(id);
\t\tapplyRequest(entity, request);
\t\treturn toResponse(repository.save(entity));
\t}}

\t@Override
\t@Transactional
\tpublic void delete(Long id) {{
\t\tif (!repository.existsById(id)) {{
\t\t\tthrow new ResourceNotFoundException("{r['name']} not found with id: " + id);
\t\t}}
\t\trepository.deleteById(id);
\t}}

\tprivate {r['name']} findOrThrow(Long id) {{
\t\treturn repository.findById(id)
\t\t\t\t.orElseThrow(() -> new ResourceNotFoundException("{r['name']} not found with id: " + id));
\t}}

\tprivate void applyRequest({r['name']} entity, {r['name']}Request request) {{
{chr(10).join(apply_lines)}
\t}}

\tprivate {r['name']}Response toResponse({r['name']} entity) {{
\t\t{r['name']}Response r = new {r['name']}Response();
{chr(10).join(resp_lines)}
\t\treturn r;
\t}}
}}
"""


def gen_admin_controller(r):
    pkg = dto_pkg(r)
    return f"""package com.bezkoder.spring.jpa.postgresql.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.postgresql.dto.{pkg}.{r['name']}Request;
import com.bezkoder.spring.jpa.postgresql.dto.{pkg}.{r['name']}Response;
import com.bezkoder.spring.jpa.postgresql.service.{r['name']}Service;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/{r['path']}")
public class Admin{r['name']}Controller {{

\tprivate final {r['name']}Service service;

\tpublic Admin{r['name']}Controller({r['name']}Service service) {{
\t\tthis.service = service;
\t}}

\t@GetMapping
\tpublic ResponseEntity<List<{r['name']}Response>> getAll() {{
\t\treturn ResponseEntity.ok(service.findAll());
\t}}

\t@GetMapping("/{{id}}")
\tpublic ResponseEntity<{r['name']}Response> getById(@PathVariable Long id) {{
\t\treturn ResponseEntity.ok(service.findById(id));
\t}}

\t@PostMapping
\tpublic ResponseEntity<{r['name']}Response> create(@Valid @RequestBody {r['name']}Request request) {{
\t\treturn ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
\t}}

\t@PutMapping("/{{id}}")
\tpublic ResponseEntity<{r['name']}Response> update(
\t\t\t@PathVariable Long id,
\t\t\t@Valid @RequestBody {r['name']}Request request) {{
\t\treturn ResponseEntity.ok(service.update(id, request));
\t}}

\t@DeleteMapping("/{{id}}")
\tpublic ResponseEntity<Void> delete(@PathVariable Long id) {{
\t\tservice.delete(id);
\t\treturn ResponseEntity.noContent().build();
\t}}
}}
"""


def main():
    (ROOT / "entity/enums").mkdir(parents=True, exist_ok=True)
    enum_path = ROOT / "entity/enums/CmsPublishStatus.java"
    if not enum_path.exists():
        enum_path.write_text("""package com.bezkoder.spring.jpa.postgresql.entity.enums;

public enum CmsPublishStatus {
\tDRAFT,
\tPUBLISHED,
\tARCHIVED
}
""")

    for r in RESOURCES:
        (ROOT / "entity").mkdir(parents=True, exist_ok=True)
        (ROOT / f"dto/{dto_pkg(r)}").mkdir(parents=True, exist_ok=True)
        (ROOT / "entity" / f"{r['name']}.java").write_text(gen_entity(r))
        (ROOT / f"dto/{dto_pkg(r)}/{r['name']}Request.java").write_text(gen_dto_request(r))
        (ROOT / f"dto/{dto_pkg(r)}/{r['name']}Response.java").write_text(gen_dto_response(r))
        (ROOT / "repository" / f"{r['name']}Repository.java").write_text(gen_repository(r))
        (ROOT / "service" / f"{r['name']}Service.java").write_text(gen_service_interface(r))
        (ROOT / "service/impl" / f"{r['name']}ServiceImpl.java").write_text(gen_service_impl(r))
        (ROOT / "controller/admin" / f"Admin{r['name']}Controller.java").write_text(gen_admin_controller(r))
        print(f"Generated {r['name']}")

    print("Done.")


if __name__ == "__main__":
    main()
