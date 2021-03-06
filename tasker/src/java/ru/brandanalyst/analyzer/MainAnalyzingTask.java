package ru.brandanalyst.analyzer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.analyzer.analyzer.quant.AbstractAnalyzer;
import ru.brandanalyst.common.scheduler.AbstractDelayedTimerTask;
import ru.brandanalyst.core.db.provider.EntityVisitor;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.model.Article;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/9/12
 * Time: 3:27 PM
 */
public class MainAnalyzingTask extends AbstractDelayedTimerTask implements InitializingBean {
    private static final Logger log = Logger.getLogger(MainAnalyzingTask.class);
    private ProvidersHandler dirtyProvidersHandler;
    private ProvidersHandler pureProvidersHandler;
    private List<AbstractAnalyzer> analyzers;

    @Required
    public void setDirtyProvidersHandler(ProvidersHandler dirtyProvidersHandler) {
        this.dirtyProvidersHandler = dirtyProvidersHandler;
    }

    @Required
    public void setPureProvidersHandler(ProvidersHandler pureProvidersHandler) {
        this.pureProvidersHandler = pureProvidersHandler;
    }

    @Required
    public void setAnalyzers(List<AbstractAnalyzer> analyzers) {
        this.analyzers = analyzers;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (AbstractAnalyzer a : analyzers) {
            a.init(pureProvidersHandler);
        }
    }

    @Override
    protected final void runTask() {
        log.info("analyzing started...");

        for (AbstractAnalyzer a : analyzers) {
            a.onStart();
        }

        dirtyProvidersHandler.getArticleProvider().visitArticles(new EntityVisitor<Article>() {
            @Override
            public void visitEntity(final Article e) {
                for (AbstractAnalyzer a : analyzers) {
                    a.analyze(e);
                }
            }
        });

        for (AbstractAnalyzer a : analyzers) {
            a.flush();
        }

        log.info("analyzing finished succesful");
    }
}
