// 处理者接口声明了一个创建处理者链的方法。还声明了一个执行请求的方法。
interface ComponentWithContextualHelp is
    method showHelp()


// 简单组件的基础类。
abstract class Component implements ComponentWithContextualHelp is
    field tooltipText: string

    // 组件容器在处理者链中作为“下一个”链接。
    protected field container: Container

    // 如果组件设定了帮助文字，那它将会显示提示信息。如果组件没有帮助文字
    // 且其容器存在，那它会将调用传递给容器。
    method showHelp() is
        if (tooltipText != null)
            // 显示提示信息。
        else
            container.showHelp()


// 容器可以将简单组件和其他容器作为其子项目。链关系将在这里建立。该类将从
// 其父类处继承 showHelp（显示帮助）的行为。
abstract class Container extends Component is
    protected field children: array of Component

    method add(child) is
        children.add(child)
        child.container = this


// 原始组件应该能够使用帮助操作的默认实现...
class Button extends Component is
    // ...

// 但复杂组件可能会对默认实现进行重写。如果无法以新的方式来提供帮助文字，
// 那组件总是还能调用基础实现的（参见 Component 类）。
class Panel extends Container is
    field modalHelpText: string

    method showHelp() is
        if (modalHelpText != null)
            // 显示包含帮助文字的模态窗口。
        else
            super.showHelp()

// ...同上...
class Dialog extends Container is
    field wikiPageURL: string

    method showHelp() is
        if (wikiPageURL != null)
            // 打开百科帮助页面。
        else
            super.showHelp()


// 客户端代码。
class Application is
    // 每个程序都能以不同方式对链进行配置。
    method createUI() is
        dialog = new Dialog("预算报告")
        dialog.wikiPageURL = "http://..."
        panel = new Panel(0, 0, 400, 800)
        panel.modalHelpText = "本面板用于..."
        ok = new Button(250, 760, 50, 20, "确认")
        ok.tooltipText = "这是一个确认按钮..."
        cancel = new Button(320, 760, 50, 20, "取消")
        // ...
        panel.add(ok)
        panel.add(cancel)
        dialog.add(panel)

    // 想象这里会发生什么。
    method onF1KeyPress() is
        component = this.getComponentAtMouseCoords()
        component.showHelp()